package com.iitca.tecnodesarrollo.service;

import com.iitca.tecnodesarrollo.dto.Lista_pozos;
import com.iitca.tecnodesarrollo.dto.Mantenimiento;
import com.iitca.tecnodesarrollo.dto.Operacion;
import com.iitca.tecnodesarrollo.dto.Parametros_calidad;
import com.iitca.tecnodesarrollo.repo.Lista_pozosRepo;
import com.iitca.tecnodesarrollo.repo.MantenimientoRepo;
import com.iitca.tecnodesarrollo.repo.OperacionRepo;
import com.iitca.tecnodesarrollo.repo.Parametros_calidadRepo;
import com.lowagie.text.*;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
public class ReporteService {

    // ==================== Paleta y tipografías ====================

    private static final Color AZUL = new Color(31, 78, 140);
    private static final Color AZUL_CLARO = new Color(239, 246, 255);
    private static final Color AZUL_MEDIO = new Color(219, 234, 254);
    private static final Color GRIS_ETIQUETA = new Color(100, 116, 139);
    private static final Color GRIS_LINEA = new Color(226, 232, 240);
    private static final Color GRIS_ZEBRA = new Color(248, 250, 252);
    private static final Color GRIS_SIN_DATO = new Color(148, 163, 184);
    private static final Color VERDE = new Color(21, 128, 61);
    private static final Color ROJO = new Color(185, 28, 28);

    private static final Font F_TITULO = new Font(Font.HELVETICA, 18, Font.BOLD, AZUL);
    private static final Font F_SUBTITULO = new Font(Font.HELVETICA, 9, Font.NORMAL, GRIS_ETIQUETA);
    private static final Font F_BANNER_CLAVE = new Font(Font.HELVETICA, 16, Font.BOLD, AZUL);
    private static final Font F_BANNER_NOMBRE = new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(51, 65, 85));
    private static final Font F_SECCION = new Font(Font.HELVETICA, 11, Font.BOLD, Color.WHITE);
    private static final Font F_GRUPO = new Font(Font.HELVETICA, 8, Font.BOLD, AZUL);
    private static final Font F_ETIQUETA = new Font(Font.HELVETICA, 8, Font.BOLD, GRIS_ETIQUETA);
    private static final Font F_VALOR = new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(15, 23, 42));
    private static final Font F_VALOR_DESTACADO = new Font(Font.HELVETICA, 10, Font.BOLD, AZUL);
    private static final Font F_SIN_DATO = new Font(Font.HELVETICA, 9, Font.ITALIC, GRIS_SIN_DATO);
    private static final Font F_POSITIVO = new Font(Font.HELVETICA, 10, Font.BOLD, ROJO);
    private static final Font F_NEGATIVO = new Font(Font.HELVETICA, 10, Font.BOLD, VERDE);
    private static final Font F_PIE = new Font(Font.HELVETICA, 8, Font.NORMAL, GRIS_ETIQUETA);

    private static final int MAX_OPERACIONES = 50;   // últimas N operaciones en el PDF
    private static final Font F_TABLA = new Font(Font.HELVETICA, 8, Font.NORMAL, Color.BLACK);
    private static final Font F_TABLA_ENC = new Font(Font.HELVETICA, 7, Font.BOLD, GRIS_ETIQUETA);

    // Logos: se buscan en el classpath (src/main/resources/img/ o src/main/resources/static/img/)
    private static final String LOGO_IZQUIERDO = "iitca_logo.png";
    private static final String LOGO_DERECHO = "xibalba_logo.png";
    private static final float LOGO_ANCHO = 110f;
    private static final float LOGO_ALTO = 46f;

    @Autowired
    private MantenimientoRepo mantenimientoRepo;

    @Autowired
    private OperacionRepo operacionRepo;

    @Autowired
    private Lista_pozosRepo pozosRepo;

    @Autowired
    private Parametros_calidadRepo parametrosCalidadRepo;

    public byte[] generarReportePozo(int id_lp) {
        // Fuera del try: si no existe, responde 404 en lugar de 500
        Lista_pozos pozo = pozosRepo.findById(id_lp)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pozo no encontrado con id " + id_lp));

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document documento = new Document(PageSize.A4, 40, 40, 40, 55);
            PdfWriter writer = PdfWriter.getInstance(documento, baos);
            writer.setPageEvent(new PieDePagina());
            documento.addTitle("Ficha Técnica del Pozo");
            documento.addAuthor("Instituto Interamericano de Tecnología y Ciencias del Agua");
            documento.addCreator("XibalbaData");
            documento.open();

            // ---------- Encabezado con logos ----------
            documento.add(crearEncabezado());
            documento.add(crearBannerPozo(pozo));

            // ---------- 1. Datos Generales ----------
            documento.add(crearSeccion("1. Datos Generales", new String[][]{
                    {"CLAVE DEL POZO", txt(pozo.getL_poz_clave(), "Sin registro"), "destacado"},
                    {"NOMBRE DE IDENTIFICACIÓN", txt(pozo.getLp_Nombre(), "Sin registro")},
                    {"RAMAL", txt(pozo.getLp_ramal(), "No asignado")},
                    {"UBICACIÓN / COORDENADAS", txt(pozo.getLp_ubicacion(), "No especificada")},
                    {"ALTURA", altura(pozo.getLp_altura())}
            }));

            // ---------- 2. Características Físicas ----------
            documento.add(crearSeccion("2. Características Físicas", new String[][]{
                    {"PROFUNDIDAD DE PERFORACIÓN", txt(pozo.getLp_Profundidad_perforación(), "0") + " metros", "destacado"},
                    {"DIÁMETRO DE PERFORACIÓN", txt(pozo.getLp_Diámetro_perforación(), "0") + " pulgadas", "destacado"},
                    {"DIÁMETRO DE ADEME", txt(pozo.getLp_Diámetro_ADEME(), "0") + " pulgadas", "destacado"}
            }));

            // ---------- 3. Equipamiento y Aportes ----------
            String subestacion = txt(pozo.getLp_subestacion(), "N/A");
            String circuito = txt(pozo.getLp_circuito_electrico(), "N/A");

            documento.add(crearSeccion("3. Equipamiento y Aportes", new String[][]{
                    {"SUBESTACIÓN / CIRCUITO", subestacion + " | " + circuito}
            }));
            documento.add(crearTablaBanderas(new String[][]{
                    {"ACUEDUCTO", txt(pozo.getLp_aporta_acueducto(), "No"),
                        "COMUNIDAD", txt(pozo.getLp_aporta_comunidad(), "No")},
                    {"RIEGO", txt(pozo.getLp_aporta_riego(), "No"),
                        "P.A. CDMX", txt(pozo.getLp_pa_cdmx(), "No")},
                    {"TELEMETRÍA", txt(pozo.getLp_telemetria(), "No"),
                        "UTR", txt(pozo.getLp_utr(), "No")}
            }));

            // ---------- 4. Parámetros de Calidad del Agua ----------
            List<Parametros_calidad> muestras = new ArrayList<>(parametrosCalidadRepo.findByPozoId(id_lp));
            muestras.sort(Comparator.comparing(Parametros_calidad::getId,
                    Comparator.nullsLast(Comparator.naturalOrder())));

            if (muestras.isEmpty()) {
                documento.add(crearSeccionAviso("4. Parámetros de Calidad del Agua",
                        "Este pozo no tiene parámetros de calidad registrados."));
            } else {
                documento.add(crearSeccionCalidad("4. Parámetros de Calidad del Agua", muestras));
            }

            // ---------- 5. Mantenimiento ----------
            String clavePozo = pozo.getL_poz_clave();
            Mantenimiento m = (clavePozo == null || clavePozo.isBlank())
                    ? null
                    : mantenimientoRepo.buscarPorClave(clavePozo.trim(), PageRequest.of(0, 1))
                            .stream().findFirst().orElse(null);
            if (m != null) {
                List<String[]> campos = new ArrayList<>();
                campo(campos, "MOTOR - TIPO", m.getMtto_motor_tipo(), "");
                campo(campos, "MOTOR - POTENCIA", m.getMtto_motor_hp(), " HP");
                campo(campos, "MOTOR - POTENCIA", m.getMtto_motor_kw(), " kW");
                campo(campos, "MOTOR - EFICIENCIA", m.getMtto_motor_eficiencia(), " %");
                campo(campos, "BOMBA - MARCA", m.getMtto_bomba_marca(), "");
                campo(campos, "BOMBA - GASTO", m.getMtto_bomba_lps(), " L/s");
                campo(campos, "BOMBA - RPM", m.getMtto_bomba_rpm(), " RPM");
                campo(campos, "BOMBA - POTENCIA", m.getMtto_bomba_hp(), " HP");
                campo(campos, "TABLERO - TIPO", m.getMtto_tablero_tipo(), "");
                campo(campos, "TABLERO - CAPACIDAD", m.getMtto_tablero_capacidad(), " A");
                campo(campos, "TABLERO - MARCA", m.getMtto_tablero_marca(), "");
                campo(campos, "TABLERO - POTENCIA", m.getMtto_tablero_hp(), " HP");
                campo(campos, "TABLERO - VOLTAJE", m.getMtto_tablero_voltaje(), " V");
                campo(campos, "TRANSFORMADOR - TIPO", m.getMtto_transformador_tipo(), "");
                campo(campos, "TRANSFORMADOR - MARCA", m.getMtto_transformador_marca(), "");
                campo(campos, "TRANSFORMADOR - CAPACIDAD", m.getMtto_transformador_capacidad(), " kVA");
                campo(campos, "TRANSFORMADOR SECO", m.getMtto_trans_seco(), "");
                campo(campos, "CABLE TRANSFORMADOR", m.getMtto_cable_transformador(), "");
                campo(campos, "CALIBRE SUMERGIBLE", m.getMtto_calibre_sumergible(), "");
                campo(campos, "LONGITUD SUMERGIBLE", m.getMtto_longitud_sumergible(), "");
                campo(campos, "CABLE BOMBA", m.getMtto_cable_bomba(), "");
                campo(campos, "CALIBRE BOMBA", m.getMtto_calibre_bomba(), "");
                campo(campos, "LONGITUD BOMBA", m.getMtto_longitud_bomba(), "");
                campo(campos, "CALIBRE ARRANCADOR", m.getMtto_calibre_arrancador(), "");
                campo(campos, "LONGITUD ARRANCADOR", m.getMtto_longitud_arrancador(), "");
                campo(campos, "TUBERÍA - DIÁMETRO", m.getMtto_tuberia_diametro(), "");
                campo(campos, "TUBERÍA - LONGITUD", m.getMtto_tuberia_longitud(), "");
                campo(campos, "POLIDUCTO", m.getMtto_poliducto(), "");
                campo(campos, "MEDIDOR DE PRESIÓN", m.getMtto_medidor_presion(), " kg/cm²");
                campo(campos, "MEDIDOR DE FLUJO", m.getMtto_medidor_flujo(), " L/s");
                campo(campos, "MEDIDOR NE", m.getMtto_medidor_NE(), "");
                campo(campos, "MEDIDOR ND", m.getMtto_medidor_ND(), "");
                campo(campos, "SISTEMA DE TIERRAS", m.getMtto_sistema_tierras(), "");
                campo(campos, "PANEL SOLAR", m.getMtto_panel_solar(), "");
                campo(campos, "BANCO DE BATERÍAS", m.getMtto_banco_baterias(), "");
                campo(campos, "ELÉCTRICA EXTERIOR", m.getMtto_electrica_exterior(), "");
                campo(campos, "ELÉCTRICA INFERIOR", m.getMtto_electrica_inferior(), "");
                campo(campos, "INSTALACIÓN SANITARIA", m.getMtto_sanitaria(), "");
                campo(campos, "FECHA DE CAPTURA", m.getMtto_fecha_captura(), "");

                String obs = m.getMtto_observaciones();
                if (!campos.isEmpty() || (obs != null && !obs.isBlank())) {
                    documento.add(crearSeccionCampos("5. Mantenimiento y Equipo Instalado", campos, obs));
                }
            }

            // ---------- 6. Operaciones ----------
            List<Operacion> operaciones = operacionRepo.buscarPorPozo(id_lp, PageRequest.of(0, MAX_OPERACIONES));
            if (!operaciones.isEmpty()) {
                List<String[]> filas = new ArrayList<>();
                for (Operacion o : operaciones) {
                    filas.add(new String[]{
                            fecha(o.getOp_fecha_captura()),
                            txt(o.getOp_operando(), "—"),
                            txt(o.getOp_nestatico(), "—"),
                            txt(o.getOp_ndinamico(), "—"),
                            txt(o.getOp_gasto(), "—"),
                            txt(o.getOp_presion(), "—"),
                            txt(o.getOp_tiempo_op(), "—"),
                            txt(o.getOp_operador(), "—"),
                            txt(o.getOp_observaciones(), "—")
                    });
                }
                documento.add(crearTablaRegistros(
                        "6. Historial de Operaciones (últimos " + MAX_OPERACIONES + " registros)",
                        new String[]{"FECHA", "OPERANDO", "N. ESTÁTICO", "N. DINÁMICO", "GASTO", "PRESIÓN", "TIEMPO OP.", "OPERADOR", "OBSERVACIONES"},
                        new float[]{13f, 9f, 8f, 8f, 8f, 8f, 8f, 12f, 26f},
                        filas));
            }

            documento.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error al ensamblar el reporte PDF", e);
        }
    }

    // ==================== Encabezado, banner y pie de página ====================

    /** Franja superior: logo IITCA a la izquierda, título al centro y logo XibalbaData a la derecha. */
    private PdfPTable crearEncabezado() throws DocumentException {
        PdfPTable tabla = new PdfPTable(3);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{26f, 48f, 26f});

        tabla.addCell(celdaLogo(cargarLogo(LOGO_IZQUIERDO), Element.ALIGN_LEFT));

        PdfPCell centro = new PdfPCell();
        centro.setBorder(Rectangle.BOTTOM);
        centro.setBorderColor(AZUL);
        centro.setBorderWidthBottom(2f);
        centro.setPaddingBottom(10);
        centro.setPaddingTop(6);

        Paragraph titulo = new Paragraph("Ficha Técnica del Pozo", F_TITULO);
        titulo.setAlignment(Element.ALIGN_CENTER);
        centro.addElement(titulo);

        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        Paragraph sub = new Paragraph("Generado el " + fecha, F_SUBTITULO);
        sub.setAlignment(Element.ALIGN_CENTER);
        centro.addElement(sub);
        tabla.addCell(centro);

        tabla.addCell(celdaLogo(cargarLogo(LOGO_DERECHO), Element.ALIGN_RIGHT));
        return tabla;
    }

    private PdfPCell celdaLogo(Image logo, int alineacion) {
        PdfPCell c = new PdfPCell();
        c.setBorder(Rectangle.BOTTOM);
        c.setBorderColor(AZUL);
        c.setBorderWidthBottom(2f);
        c.setPaddingBottom(10);
        c.setPaddingTop(2);
        if (logo != null) {
            logo.setAlignment(alineacion);
            c.addElement(logo);
        } else {
            c.addElement(new Phrase(""));   // sin logo: la celda queda vacía y el reporte se genera igual
        }
        return c;
    }

    /** Busca el logo en el classpath; si no existe devuelve null (el PDF se genera sin él). */
    private Image cargarLogo(String archivo) {
        for (String ruta : new String[]{"img/" + archivo, "static/img/" + archivo}) {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream(ruta)) {
                if (in != null) {
                    Image img = Image.getInstance(in.readAllBytes());
                    img.scaleToFit(LOGO_ANCHO, LOGO_ALTO);
                    return img;
                }
            } catch (Exception e) {
                System.err.println("No se pudo cargar el logo " + ruta + ": " + e.getMessage());
            }
        }
        System.err.println("Logo no encontrado en el classpath: " + archivo);
        return null;
    }

    /** Recuadro con la clave y el nombre del pozo, justo debajo del encabezado. */
    private PdfPTable crearBannerPozo(Lista_pozos pozo) {
        PdfPTable tabla = new PdfPTable(1);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(14);

        PdfPCell c = new PdfPCell();
        c.setBackgroundColor(AZUL_CLARO);
        c.setBorder(Rectangle.LEFT);
        c.setBorderColorLeft(AZUL);
        c.setBorderWidthLeft(4f);
        c.setPadding(12);
        c.addElement(new Paragraph(txt(pozo.getL_poz_clave(), "Sin clave"), F_BANNER_CLAVE));

        String ramal = txt(pozo.getLp_ramal(), "");
        String detalle = txt(pozo.getLp_Nombre(), "Sin nombre") + (ramal.isEmpty() ? "" : "  |  Ramal: " + ramal);
        c.addElement(new Paragraph(detalle, F_BANNER_NOMBRE));
        tabla.addCell(c);
        return tabla;
    }

    /** Línea y numeración al pie de cada página. */
    private static class PieDePagina extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            float izq = document.left();
            float der = document.right();
            float y = document.bottom() - 20;

            cb.setColorStroke(GRIS_LINEA);
            cb.setLineWidth(0.8f);
            cb.moveTo(izq, y + 12);
            cb.lineTo(der, y + 12);
            cb.stroke();

            ColumnText.showTextAligned(cb, Element.ALIGN_LEFT,
                    new Phrase("XibalbaData  ·  Instituto Interamericano de Tecnología y Ciencias del Agua", F_PIE),
                    izq, y, 0);
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
                    new Phrase("Página " + writer.getPageNumber(), F_PIE),
                    der, y, 0);
        }
    }

    // ==================== Secciones ====================

    /** Sección con encabezado azul y filas etiqueta / valor (2 columnas), con filas alternadas. */
    private PdfPTable crearSeccion(String titulo, String[][] filas) throws DocumentException {
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{40f, 60f});
        tabla.setSpacingBefore(12);
        tabla.setKeepTogether(true);

        tabla.addCell(cabeceraSeccion(titulo, 2));

        int i = 0;
        for (String[] fila : filas) {
            Color fondo = (i++ % 2 == 1) ? GRIS_ZEBRA : null;
            boolean destacado = fila.length > 2 && "destacado".equals(fila[2]);
            tabla.addCell(celda(fila[0], F_ETIQUETA, fondo));
            tabla.addCell(celda(fila[1], destacado ? F_VALOR_DESTACADO : F_VALOR, fondo));
        }
        return tabla;
    }

    /** Tabla de 4 columnas: etiqueta, valor, etiqueta, valor (Sí/No). */
    private PdfPTable crearTablaBanderas(String[][] filas) throws DocumentException {
        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{22f, 28f, 22f, 28f});
        tabla.setKeepTogether(true);

        int r = 0;
        for (String[] fila : filas) {
            Color fondo = (r++ % 2 == 1) ? GRIS_ZEBRA : null;
            for (int i = 0; i < 4; i++) {
                tabla.addCell(celda(fila[i], i % 2 == 0 ? F_ETIQUETA : F_VALOR, fondo));
            }
        }
        return tabla;
    }

    /** Sección de una sola fila con un mensaje (por ejemplo, "sin datos registrados"). */
    private PdfPTable crearSeccionAviso(String titulo, String mensaje) throws DocumentException {
        PdfPTable tabla = new PdfPTable(1);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(12);
        tabla.setKeepTogether(true);

        tabla.addCell(cabeceraSeccion(titulo, 1));
        tabla.addCell(celda(mensaje, F_SIN_DATO, null));
        return tabla;
    }

    // ---------- Parámetros de calidad ----------

    /**
     * Parámetros de calidad en 3 grupos (campo, química y microbiología).
     * Si el pozo tiene varias muestras, cada una lleva su subtítulo.
     */
    private PdfPTable crearSeccionCalidad(String titulo, List<Parametros_calidad> muestras)
            throws DocumentException {
        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{27f, 23f, 27f, 23f, 23f, 23f});
        tabla.setSpacingBefore(12);
        tabla.setHeaderRows(1);
        tabla.setKeepTogether(muestras.size() == 1);

        tabla.addCell(cabeceraSeccion(titulo, 6));

        int n = 1;
        for (Parametros_calidad p : muestras) {
            if (muestras.size() > 1) {
                tabla.addCell(subcabecera("MUESTRA " + n++, 6, AZUL_MEDIO));
            }

            agregarGrupoCalidad(tabla, "PARÁMETROS DE CAMPO", List.of(
                    new String[]{"TEMPERATURA", numero(p.getTemperaturaC(), "°C")},
                    new String[]{"pH", numero(p.getPh(), "")},
                    new String[]{"CONDUCTIVIDAD ELÉCTRICA (CE)", numero(p.getCeUsCm(), "µS/cm")},
                    new String[]{"SÓLIDOS DISUELTOS TOTALES (SDT)", numero(p.getSdtPpm(), "ppm")},
                    new String[]{"POTENCIAL REDOX (Eh)", numero(p.getEhMv(), "mV")}));

            agregarGrupoCalidad(tabla, "QUÍMICA DEL AGUA", List.of(
                    new String[]{"OXÍGENO DISUELTO (SATURACIÓN)", numero(p.getOdPorcentaje(), "%")},
                    new String[]{"OXÍGENO DISUELTO (OD)", numero(p.getOdMgL(), "mg/L")},
                    new String[]{"CLORUROS (Cl-)", numero(p.getClorurosMgL(), "mg/L")},
                    new String[]{"NITRATOS (N-NO3-)", textoConUnidad(p.getNitratosMgL(), "mg/L")},
                    new String[]{"SULFATOS (SO4 2-)", numero(p.getSulfatosMgL(), "mg/L")},
                    new String[]{"ALCALINIDAD", numero(p.getAlcalinidadMgCaco3L(), "mg CaCO3/L")},
                    new String[]{"BICARBONATOS (HCO3-)", numero(p.getBicarbonatosMgL(), "mg/L")}));

            agregarGrupoCalidad(tabla, "MICROBIOLOGÍA", List.of(
                    new String[]{"COLIFORMES TOTALES", textoLimpio(p.getColiformesTotales()), "resultado"},
                    new String[]{"E. COLI", textoLimpio(p.geteColi()), "resultado"}));
        }
        return tabla;
    }

    private void agregarGrupoCalidad(PdfPTable tabla, String grupo, List<String[]> campos) {
        tabla.addCell(subcabecera(grupo, 6, AZUL_CLARO));

        Color fondo = null;
        for (int i = 0; i < campos.size(); i++) {
            String[] c = campos.get(i);
            fondo = ((i / 2) % 2 == 1) ? GRIS_ZEBRA : null;
            tabla.addCell(celda(c[0], F_ETIQUETA, fondo));
            tabla.addCell(celdaValorCalidad(c, fondo));
        }
        if (campos.size() % 2 != 0) {          // completa la última fila
            tabla.addCell(celda("", F_ETIQUETA, fondo));
            tabla.addCell(celda("", F_VALOR, fondo));
        }
    }

    private PdfPCell celdaValorCalidad(String[] campo, Color fondo) {
        String valor = campo[1];
        if (valor == null) {
            return celda("Sin dato", F_SIN_DATO, fondo);
        }
        if (campo.length > 2 && "resultado".equals(campo[2])) {
            String v = valor.toLowerCase();
            Font f = v.startsWith("pos") ? F_POSITIVO : (v.startsWith("neg") ? F_NEGATIVO : F_VALOR);
            return celda(valor, f, fondo);
        }
        return celda(valor, F_VALOR_DESTACADO, fondo);
    }

    // ---------- Mantenimiento ----------

    /** Agrega un campo solo si tiene valor; así el PDF no se llena de "—". */
    private void campo(List<String[]> lista, String etiqueta, Object valor, String unidad) {
        if (valor == null) return;
        String texto = (valor instanceof LocalDateTime d) ? fecha(d) : String.valueOf(valor).trim();
        if (texto.isEmpty()) return;
        lista.add(new String[]{etiqueta, texto + unidad});
    }

    /** Sección de 4 columnas (etiqueta, valor, etiqueta, valor) con observaciones opcionales a todo el ancho. */
    private PdfPTable crearSeccionCampos(String titulo, List<String[]> campos, String observaciones)
            throws DocumentException {
        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{24f, 26f, 24f, 26f, 24f, 26f});
        tabla.setSpacingBefore(12);
        tabla.setHeaderRows(1);

        tabla.addCell(cabeceraSeccion(titulo, 6));

        Color fondo = null;
        for (int i = 0; i < campos.size(); i++) {
            String[] c = campos.get(i);
            fondo = ((i / 2) % 2 == 1) ? GRIS_ZEBRA : null;
            tabla.addCell(celda(c[0], F_ETIQUETA, fondo));
            tabla.addCell(celda(c[1], F_VALOR, fondo));
        }
        if (campos.size() % 2 != 0) {          // completa la última fila
            tabla.addCell(celda("", F_ETIQUETA, fondo));
            tabla.addCell(celda("", F_VALOR, fondo));
        }

        if (observaciones != null && !observaciones.isBlank()) {
            tabla.addCell(celda("OBSERVACIONES", F_ETIQUETA, null));
            PdfPCell obs = celda(observaciones.trim(), F_VALOR, null);
            obs.setColspan(5);
            tabla.addCell(obs);
        }
        return tabla;
    }

    // ---------- Operaciones ----------

    /** Tabla de historial. Repite título y encabezados al cambiar de página. */
    private PdfPTable crearTablaRegistros(String titulo, String[] encabezados,
                                        float[] anchos, List<String[]> filas) throws DocumentException {
        PdfPTable tabla = new PdfPTable(encabezados.length);
        tabla.setWidthPercentage(100);
        tabla.setWidths(anchos);
        tabla.setSpacingBefore(12);
        tabla.setHeaderRows(2);

        tabla.addCell(cabeceraSeccion(titulo, encabezados.length));

        for (String h : encabezados) {
            PdfPCell c = celda(h, F_TABLA_ENC, new Color(241, 245, 249));
            c.setPadding(4);
            tabla.addCell(c);
        }
        int r = 0;
        for (String[] fila : filas) {
            Color fondo = (r++ % 2 == 1) ? GRIS_ZEBRA : null;
            for (String valor : fila) {
                PdfPCell c = celda(valor, F_TABLA, fondo);
                c.setPadding(4);
                tabla.addCell(c);
            }
        }
        return tabla;
    }

    // ==================== Helpers ====================

    /** Barra azul con el título de la sección. */
    private PdfPCell cabeceraSeccion(String titulo, int colspan) {
        PdfPCell cabecera = new PdfPCell(new Phrase(titulo, F_SECCION));
        cabecera.setColspan(colspan);
        cabecera.setBackgroundColor(AZUL);
        cabecera.setPadding(8);
        cabecera.setPaddingLeft(10);
        cabecera.setBorder(Rectangle.NO_BORDER);
        return cabecera;
    }

    /** Banda clara para subtítulos dentro de una sección (grupos, muestras). */
    private PdfPCell subcabecera(String texto, int colspan, Color fondo) {
        PdfPCell c = new PdfPCell(new Phrase(texto, F_GRUPO));
        c.setColspan(colspan);
        c.setBackgroundColor(fondo);
        c.setPadding(5);
        c.setPaddingLeft(10);
        c.setBorder(Rectangle.NO_BORDER);
        return c;
    }

    private PdfPCell celda(String texto, Font fuente, Color fondo) {
        PdfPCell c = new PdfPCell(new Phrase(texto, fuente));
        c.setPadding(6);
        c.setPaddingLeft(10);
        c.setBorder(Rectangle.BOTTOM);
        c.setBorderColor(GRIS_LINEA);
        if (fondo != null) c.setBackgroundColor(fondo);
        return c;
    }

    /** Convierte cualquier valor a texto y evita "null". */
    private String txt(Object valor, String porDefecto) {
        if (valor == null) return porDefecto;
        String s = String.valueOf(valor).trim();
        return s.isEmpty() ? porDefecto : s;
    }

    /** Texto sin espacios sobrantes; null si está vacío (se imprime como "Sin dato"). */
    private String textoLimpio(String valor) {
        if (valor == null) return null;
        String s = valor.trim();
        return s.isEmpty() ? null : s;
    }

    /** Número con máximo 2 decimales y su unidad; null si no hay valor. */
    private String numero(Double valor, String unidad) {
        if (valor == null || valor.isNaN() || valor.isInfinite()) return null;
        String s = BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP)
                .stripTrailingZeros().toPlainString();
        return unidad.isEmpty() ? s : s + " " + unidad;
    }

    /** La unidad solo se agrega si el texto empieza con un número (no a "N.D"). */
    private String textoConUnidad(String valor, String unidad) {
        String s = textoLimpio(valor);
        if (s == null) return null;
        return Character.isDigit(s.charAt(0)) ? s + " " + unidad : s;
    }

    /** Si la altura es un número puro se le agrega "msnm"; si ya trae texto se deja tal cual. */
    private String altura(String valor) {
        String s = txt(valor, "");
        if (s.isEmpty()) return "No especificada";
        return s.matches("\\d+(\\.\\d+)?") ? s + " msnm" : s;
    }

    private String fecha(LocalDateTime f) {
        return f == null ? "—" : f.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

}