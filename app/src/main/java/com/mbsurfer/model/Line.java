package com.mbsurfer.model;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import com.mbsurfer.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by GrzegorzFeathers on 1/6/15.
 */
public enum Line {

    //App Color #BF381A
    LINE_1("Line 1", "#771B0C", R.drawable.bg_title_line1, R.drawable.ic_line1,
           R.drawable.bg_line1, R.drawable.ic_marker_line1),
    LINE_2("Line 2", "#523C91", R.drawable.bg_title_line2, R.drawable.ic_line2,
           R.drawable.bg_line2, R.drawable.ic_marker_line2),
    LINE_3("Line 3", "#6E8243", R.drawable.bg_title_line3, R.drawable.ic_line3,
           R.drawable.bg_line3, R.drawable.ic_marker_line3),
    LINE_4("Line 4", "#E9AF32", R.drawable.bg_title_line4, R.drawable.ic_line4,
           R.drawable.bg_line4, R.drawable.ic_marker_line4);

    private String lineName;
    private String lineColor;
    private int titleBackground;
    private int lineIconId;
    private int lineBackground;
    private int lineMarkerId;
    private List<Station> stations;
    //private StationsItemizedOverlay itemizedOverlay = null;

    private Line(String lineName, String lineColor, int titleBackground,
                        int lineIconId, int lineBackground, int lineMarker) {
        this.lineName = lineName;
        this.lineColor = lineColor;
        this.titleBackground = titleBackground;
        this.lineIconId = lineIconId;
        this.lineBackground = lineBackground;
        this.lineMarkerId = lineMarker;
        this.fillLine();
    }

    private void fillLine() {
        this.stations = new ArrayList<Station>();
        switch (this.ordinal()) {
            case 0:
                this.fill_1();
                break;
            case 1:
                this.fill_2();
                break;
            case 2:
                this.fill_3();
                break;
            case 3:
                this.fill_4();
                break;
        }
    }

    @Override
    public String toString() {
        return this.lineName;
    }

    public String getLineColor() {
        return this.lineColor;
    }

    public int getLineBackground() {
        return this.lineBackground;
    }

    public int getTitleBackground() {
        return this.titleBackground;
    }

    public int getLineMarkerId(){
        return this.lineMarkerId;
    }

    public BitmapDescriptor getMarkerBitmapDescriptor(){
        return BitmapDescriptorFactory.fromResource(this.lineMarkerId);
    }

    public List<Station> getStations() {
        return this.stations;
    }

    public int getLineIconId(){
        return this.lineIconId;
    }

    static public ArrayList<Station> searchFor(String query) {
        ArrayList<Station> results = new ArrayList<Station>();

        for (int i = 0 ; i < Line.values().length ; i++) {
            for (Station station : Line.values()[i].getStations()) {
                if (station.getName().toLowerCase()
                        .contains(query.toLowerCase())) {
                    results.add(station);
                }
            }
        }

        return results;
    }

    private void fill_1() {
        //*
        this.stations.add(new Station(this, R.drawable.line1_el_caminero,
                                      "El Caminero", -99.169273,19.279104));
        this.stations.add(new Station(this, R.drawable.line1_la_joya,
                                      "La Joya", -99.171440,19.280481));
        this.stations.add(new Station(this, R.drawable.line1_santa_ursula,
                                      "Santa òrsula", -99.175621,19.283804));
        this.stations.add(new Station(this, R.drawable.line1_fuentes_brotantes,
                                      "Fuentes Brotantes", -99.174355,19.288704));
        this.stations.add(new Station(this, R.drawable.line1_ayuntamiento,
                                      "Ayuntamiento", -99.178261,19.292816));
        this.stations.add(new Station(this, R.drawable.line1_corregidora,
                                      "Corregidora", -99.181778,19.294334));
        this.stations.add(new Station(this, R.drawable.line1_villa_olimpica,
                                      "Villa Olmpica", -99.185410,19.298285));
        this.stations.add(new Station(this, R.drawable.line1_perisur,
                                      "Perisur", -99.186226,19.304886));
        this.stations.add(new Station(this, R.drawable.line1_ccu,
                                      "Centro Cultural Universitario", -99.187447,19.314140));
        this.stations.add(new Station(this, R.drawable.line1_cu,
                                      "Ciudad Universitaria", -99.188560,19.323496));
        this.stations.add(new Station(this, R.drawable.line1_doctor_galvez,
                                      "Doctor Glvez", -99.190323,19.340139));
        this.stations.add(new Station(this, R.drawable.line1_la_bombilla,
                                      "La Bombilla", -99.187706,19.346821));
        this.stations.add(new Station(this, R.drawable.line1_altavista,
                                      "Altavista", -99.186333,19.351112));
        this.stations.add(new Station(this, R.drawable.line1_olivo,
                                      "Olivo", -99.184868,19.355627));
        this.stations.add(new Station(this, R.drawable.line1_francia,
                                      "Francia", -99.183838,19.358685));
        this.stations.add(new Station(this, R.drawable.line1_jose_maria_velazco,
                                      "Jos Mara Velasco", -99.182770,19.361874));
        this.stations.add(new Station(this, R.drawable.line1_teatro_insurgentes,
                                      "Teatro Insurgentes", -99.181923,19.364555));
        this.stations.add(new Station(this, R.drawable.line1_rio_churubusco,
                                      "Ro Churubusco", -99.180717,19.368391));
        this.stations.add(new Station(this, R.drawable.line1_felix_cuevas,
                                      "Flix Cuevas", -99.178902,19.373886));
        this.stations.add(new Station(this, R.drawable.line1_parque_hundido,
                                      "Parque Hundido", -99.176933,19.379898));
        this.stations.add(new Station(this, R.drawable.line1_ciudad_deportes,
                                      "Ciudad de los Deportes", -99.176048,19.382732));
        this.stations.add(new Station(this, R.drawable.line1_colonia_valle,
                                      "Colonia del Valle", -99.175041,19.385912));
        this.stations.add(new Station(this, R.drawable.line1_napoles,
                                      "Npoles", -99.173691,19.390081));
        this.stations.add(new Station(this, R.drawable.line1_poliforum,
                                      "Poliforum", -99.172729,19.393118));
        this.stations.add(new Station(this, R.drawable.line1_la_piedad,
                                      "La Piedad", -99.171074,19.398136));
        this.stations.add(new Station(this, R.drawable.line1_nuevo_leon,
                                      "Nuevo Len", -99.169701,19.402285));
        this.stations.add(new Station(this, R.drawable.line1_chilpancingo,
                                      "Chilpancingo", -99.168480,19.406111));
        this.stations.add(new Station(this, R.drawable.line1_campeche,
                                      "Campeche", -99.167427,19.409531));
        this.stations.add(new Station(this, R.drawable.line1_sonora,
                                      "Sonora", -99.166222,19.413033));
        this.stations.add(new Station(this, R.drawable.line1_alvaro_obregon,
                                      "çlvaro Obregn", -99.165192,19.416189));
        this.stations.add(new Station(this, R.drawable.line1_durango,
                                      "Durango", -99.164185,19.419548));
        this.stations.add(new Station(this, R.drawable.line1_insurgentes,
                                      "Insurgentes", -99.162361,19.423372));
        this.stations.add(new Station(this, R.drawable.line1_hamburgo,
                                      "Hamburgo", -99.161377,19.427217));
        this.stations.add(new Station(this, R.drawable.line1_reforma,
                                      "Reforma", -99.158646,19.433086));
        this.stations.add(new Station(this, R.drawable.line1_plaza_republica,
                                      "Plaza de la Repblica", -99.157211,19.436445));
        this.stations.add(new Station(this, R.drawable.line1_revolucion,
                                      "Revolucin", -99.155563,19.440046));
        this.stations.add(new Station(this, R.drawable.line1_el_chopo,
                                      "El Chopo", -99.154572,19.442860));
        this.stations.add(new Station(this, R.drawable.line1_buenavista,
                                      "Buenavista", -99.153175,19.446724));
        this.stations.add(new Station(this, R.drawable.line1_manuel_gonzales,
                                      "Manuel Gonzlez", -99.149231,19.456861));
        this.stations.add(new Station(this, R.drawable.line1_san_simon,
                                      "San Simn", -99.146782,19.459208));
        this.stations.add(new Station(this, R.drawable.line1_circuito,
                                      "Circuito", -99.144234,19.462383));
        this.stations.add(new Station(this, R.drawable.line1_la_raza,
                                      "La Raza", -99.139725,19.467848));
        this.stations.add(new Station(this, R.drawable.line1_potrero,
                                      "Potrero", -99.132362,19.476748));
        this.stations.add(new Station(this, R.drawable.line1_euzkaro,
                                      "Euzkaro", -99.126953,19.483362));
        this.stations.add(new Station(this, R.drawable.line1_deportivo_18_marzo,
                                      "Deportivo 18 de Marzo", -99.124588,19.486265));
        this.stations.add(new Station(this, R.drawable.line1_indios_verdes,
                                      "Indios Verdes", -99.120026,19.493103));
	/**/
    }

    private void fill_2() {
        //*
        this.stations.add(new Station(this, R.drawable.line2_tacubaya,
                                      "Tacubaya", -99.187096,19.401455));
        this.stations.add(new Station(this, R.drawable.line2_antonio_maceo,
                                      "Antonio Maceo", -99.187096,19.401455));
        this.stations.add(new Station(this, R.drawable.line2_parque_lira,
                                      "Parque Lira", -99.189354,19.407749));
        this.stations.add(new Station(this, R.drawable.line2_de_la_salle,
                                      "De La Salle", -99.183914,19.407639));
        this.stations.add(new Station(this, R.drawable.line2_patriotismo,
                                      "Patriotismo", -99.177315,19.405441));
        this.stations.add(new Station(this, R.drawable.line2_escadon,
                                      "Escandn", -99.174316,19.404470));
        this.stations.add(new Station(this, R.drawable.line2_nuevo_leon,
                                      "Nuevo Len", -99.169579,19.403570));
        this.stations.add(new Station(this, R.drawable.line2_viaducto,
                                      "Viaducto", -99.168030,19.400928));
        this.stations.add(new Station(this, R.drawable.line2_amores,
                                      "Amores", -99.163582,19.396799));
        this.stations.add(new Station(this, R.drawable.line2_etiopia,
                                      "Etiopa - Plaza de la Transparencia", -99.155518,19.395788));
        this.stations.add(new Station(this, R.drawable.line2_doctor_vertiz,
                                      "Dr. Vrtiz", -99.151527,19.395666));
        this.stations.add(new Station(this, R.drawable.line2_centro_scop,
                                      "Centro SCOP", -99.146484,19.395243));
        this.stations.add(new Station(this, R.drawable.line2_alamos,
                                      "çlamos", -99.142448,19.394686));
        this.stations.add(new Station(this, R.drawable.line2_xola,
                                      "Xola", -99.139763,19.394297));
        this.stations.add(new Station(this, R.drawable.line2_las_americas,
                                      "Las Amricas", -99.134865,19.393511));
        this.stations.add(new Station(this, R.drawable.line2_andres_molina,
                                      "Andrs Molina", -99.129303,19.397802));
        this.stations.add(new Station(this, R.drawable.line2_la_viga,
                                      "La Viga", -99.124794,19.398066));
        this.stations.add(new Station(this, R.drawable.line2_coyuya,
                                      "Coyuya", -99.117149,19.398247));
        this.stations.add(new Station(this, R.drawable.line2_canela,
                                      "Canela", -99.107956,19.397781));
        this.stations.add(new Station(this, R.drawable.line2_tlacotal,
                                      "Tlacotal", -99.102455,19.397154));
        this.stations.add(new Station(this, R.drawable.line2_goma,
                                      "Goma", -99.099388,19.396872));
        this.stations.add(new Station(this, R.drawable.line2_iztacalco,
                                      "Iztacalco", -99.095474,19.396517));
        this.stations.add(new Station(this, R.drawable.line2_upiicsa,
                                      "UPIICSA", -99.089951,19.393623));
        this.stations.add(new Station(this, R.drawable.line2_el_rodeo,
                                      "El Rodeo", -99.087990,19.392277));
        this.stations.add(new Station(this, R.drawable.line2_rio_tecolutla,
                                      "Ro Tecolutla", -99.083679,19.389372));
        this.stations.add(new Station(this, R.drawable.line2_rio_mayo,
                                      "Ro Mayo", -99.080177,19.387045));
        this.stations.add(new Station(this, R.drawable.line2_rojo_gomez,
                                      "Rojo Gmez", -99.076187,19.384342));
        this.stations.add(new Station(this, R.drawable.line2_rio_frio,
                                      "Ro Fro", -99.074715,19.387863));
        this.stations.add(new Station(this, R.drawable.line2_del_moral,
                                      "Del Moral", -99.070564,19.384180));
        this.stations.add(new Station(this, R.drawable.line2_leyes_reforma,
                                      "Leyes de Reforma", -99.066467,19.383583));
        this.stations.add(new Station(this, R.drawable.line2_cch_oriente,
                                      "CCH Oriente", -99.060944,19.382885));
        this.stations.add(new Station(this,
                                      R.drawable.line2_constiticion_apatzingan,
                                      "Constitucin de Apatzingan", -99.059845,19.389534));
        this.stations.add(new Station(this, R.drawable.line2_canal_san_juan,
                                      "Canal de San Juan", -99.055031,19.395868));
        this.stations.add(new Station(this, R.drawable.line2_nicolas_bravo,
                                      "Nicols Bravo", -99.050468,19.393057));
        this.stations.add(new Station(this, R.drawable.line2_general_leon,
                                      "General Len", -99.051918,19.385162));
        this.stations.add(new Station(this, R.drawable.line2_tepalcates,
                                      "Tepalcates", -99.046272,19.390709));
	/**/
    }

    private void fill_3() {
        //*
        this.stations.add(new Station(this, R.drawable.line3_tenayuca,
                                      "Tenayuca", -99.170105,19.529377));
        this.stations.add(new Station(this, R.drawable.line3_san_jose_escalera,
                                      "San Jos de la Escalera", -99.166405,19.523573));
        this.stations.add(new Station(this, R.drawable.line3_progreso_nacional,
                                      "Progreso Nacional", -99.163437,19.518679));
        this.stations.add(new Station(this, R.drawable.line3_tres_anegas,
                                      "Tres Anegas", -99.161720,19.514816));
        this.stations.add(new Station(this, R.drawable.line3_jupiter,
                                      "Jpiter", -99.158905,19.507736));
        this.stations.add(new Station(this, R.drawable.line3_la_patera,
                                      "La Patera", -99.156998,19.503084));
        this.stations.add(new Station(this, R.drawable.line3_poniente_146,
                                      "Poniente 146", -99.155624,19.499222));
        this.stations.add(new Station(this, R.drawable.line3_montevideo,
                                      "Montevideo", -99.154182,19.495378));
        this.stations.add(new Station(this, R.drawable.line3_poniente_134,
                                      "Poniente 134", -99.152992,19.492273));
        this.stations.add(new Station(this, R.drawable.line3_poniente_128,
                                      "Poniente 128", -99.151764,19.489017));
        this.stations.add(new Station(this, R.drawable.line3_magdalena_salinas,
                                      "Magdalena de las Salinas", -99.149666,19.483435));
        this.stations.add(new Station(this, R.drawable.line3_coltongo,
                                      "Coltongo", -99.148071,19.479176));
        this.stations.add(new Station(this, R.drawable.line3_cuitlahuac,
                                      "Cuitlhuac", -99.145927,19.473532));
        this.stations.add(new Station(this, R.drawable.line3_heroe_nacozari,
                                      "Hroe de Nacozari", -99.144806,19.470497));
        this.stations.add(new Station(this, R.drawable.line3_hospital_raza,
                                      "Hospital La Raza", -99.143219,19.466146));
        this.stations.add(new Station(this, R.drawable.line3_la_raza,
                                      "La Raza", -99.141418,19.467806));
        this.stations.add(new Station(this, R.drawable.line3_circuito,
                                      "Circuito", -99.144356,19.462891));
        this.stations.add(new Station(this, R.drawable.line3_tolnahuac,
                                      "Tolnhuac", -99.143982,19.460423));
        this.stations.add(new Station(this, R.drawable.line3_tlatelolco,
                                      "Tlatelolco", -99.144958,19.456011));
        this.stations.add(new Station(this,R.drawable.line3_ricardo_flores_magon,
                                      "Ricardo Flores Magn", -99.145821,19.452238));
        this.stations.add(new Station(this, R.drawable.line3_guerrero,
                                      "Guerrero", -99.147385,19.445208));
        this.stations.add(new Station(this, R.drawable.line3_buenavista,
                                      "Buenavista", -99.151978,19.446136));
        this.stations.add(new Station(this, R.drawable.line3_mina,
                                      "Mina", -99.149124,19.438023));
        this.stations.add(new Station(this, R.drawable.line3_hidalgo,
                                      "Hidalgo", -99.147278,19.435493));
        this.stations.add(new Station(this, R.drawable.line3_juarez,
                                      "Jurez", -99.147964,19.431973));
        this.stations.add(new Station(this, R.drawable.line3_balderas,
                                      "Balderas", -99.148972,19.427481));
        this.stations.add(new Station(this, R.drawable.line3_cuauhtemoc,
                                      "Cuauhtmoc", -99.153831,19.424404));
        this.stations.add(new Station(this, R.drawable.line3_jardin_pushkin,
                                      "Jardn Pushkin", -99.154182,19.419670));
        this.stations.add(new Station(this, R.drawable.line3_hospital_general,
                                      "Hospital General", -99.154572,19.414904));
        this.stations.add(new Station(this, R.drawable.line3_doctor_marquez,
                                      "Doctor Mrquez", -99.154831,19.411615));
        this.stations.add(new Station(this, R.drawable.line3_centro_medico,
                                      "Centro Mdico", -99.155205,19.406717));
        this.stations.add(new Station(this, R.drawable.line3_obrero_mundial,
                                      "Obrero Mundial", -99.155495,19.403316));
        this.stations.add(new Station(this, R.drawable.line3_etiopia,
                                      "Etiopa - Plaza de la Transparencia", -99.156029,19.396132));
	/**/
    }

    private void fill_4() {
        //*
        this.stations.add(new Station(this, R.drawable.line4_buenavista_iv,
                                      "Buenavista IV", -99.152320,19.445661));
        this.stations.add(new Station(this, R.drawable.line4_delegacion_cuauhtemoc,
                                      "Delegacin Cuauhtmoc", -99.152376,19.443472));
        this.stations.add(new Station(this, R.drawable.line4_puente_alvarado,
                                      "Puente de Alvarado", -99.153331,19.438894));
        this.stations.add(new Station(this, R.drawable.line4_museo_san_carlos,
                                      "Museo de San Carlos", -99.149542,19.437975));
        this.stations.add(new Station(this, R.drawable.line4_hidalgo,
                                      "Hidalgo", -99.145097,19.437158));
        this.stations.add(new Station(this, R.drawable.line4_bellas_artes,
                                      "Bellas Artes", -99.140980,19.436340));
        this.stations.add(new Station(this, R.drawable.line4_teatro_blanquita,
                                      "Teatro Blanquita", -99.139892,19.438673));
        this.stations.add(new Station(this, R.drawable.line4_republica_chile,
                                      "Repblica de Chile", -99.135450,19.437950));
        this.stations.add(new Station(this, R.drawable.line4_republica_argentina,
                                      "Repblica de Argentina", -99.131559,19.437421));
        this.stations.add(new Station(this, R.drawable.line4_teatro_pueblo,
                                      "Teatro del Pueblo", -99.127206,19.436823));
        this.stations.add(new Station(this, R.drawable.line4_mixcalco,
                                      "Mixcalco", -99.123704,19.436387));
        this.stations.add(new Station(this, R.drawable.line4_ferrocarril_cintura,
                                      "Ferrocarril de Cintura", -99.120990,19.436110));
        this.stations.add(new Station(this, R.drawable.line4_morelos,
                                      "Morelos", -99.118286,19.435790));
        this.stations.add(new Station(this,R.drawable.line4_archivo_general_nacion,
                                      "Archivo General de la Nacin", -99.114827,19.435110));
        this.stations.add(new Station(this, R.drawable.line4_san_lazaro,
                                      "San Lzaro", -99.115106,19.430592));
        this.stations.add(new Station(this, R.drawable.line4_aeropuerto_t1,
                                      "Aeropuerto T1", -99.087724,19.433852));
        this.stations.add(new Station(this, R.drawable.line4_aeropuerto_t2,
                                      "Aeropuerto T2", -99.077734,19.419081));
        this.stations.add(new Station(this, R.drawable.line4_moctezuma,
                                      "Moctezuma", -99.112200,19.426890));
        this.stations.add(new Station(this,R.drawable.line4_ing_eduardo_molina,
                                      "Eduardo Molina", -99.116009,19.427285));
        this.stations.add(new Station(this, R.drawable.line4_cecilio_robelo,
                                      "Cecilio Robelo", -99.119844,19.425505));
        this.stations.add(new Station(this, R.drawable.line4_hospital_balbuena,
                                      "Hospital Balbuena", -99.116162,19.425043));
        this.stations.add(new Station(this, R.drawable.line4_mercado_sonora,
                                      "Mercado de Sonora", -99.123136,19.423183));
        this.stations.add(new Station(this, R.drawable.line4_la_merced,
                                      "La Merced", -99.125713,19.425505));
        this.stations.add(new Station(this, R.drawable.line4_circunvalacion,
                                      "Circunvalacin", -99.125287,19.428340));
        this.stations.add(new Station(this, R.drawable.line4_las_cruces,
                                      "Las Cruces", -99.129667,19.426132));
        this.stations.add(new Station(this, R.drawable.line4_museo_ciudad,
                                      "Museo de la Ciudad", -99.132656,19.429073));
        this.stations.add(new Station(this, R.drawable.line4_pino_suarez,
                                      "Pino Surez", -99.132903,19.426563));
        this.stations.add(new Station(this,R.drawable.line4_isabel_la_catolica,
                                      "Isabel La Catlica", -99.136797,19.429665));
        this.stations.add(new Station(this, R.drawable.line4_el_salvador,
                                      "El Salvador", -99.138910,19.429986));
        this.stations.add(new Station(this, R.drawable.line4_eje_central,
                                      "Eje Central", -99.141487,19.430288));
        this.stations.add(new Station(this, R.drawable.line4_plaza_san_juan,
                                      "Plaza San Juan", -99.144352,19.430722));
        this.stations.add(new Station(this, R.drawable.line4_juarez,
                                      "Jurez", -99.148052,19.431287));
        this.stations.add(new Station(this, R.drawable.line4_vocacional_5,
                                      "Vocacional 5", -99.151248,19.431756));
        this.stations.add(new Station(this, R.drawable.line4_expo_reforma,
                                      "Expo Reforma", -99.150800,19.433380));
        this.stations.add(new Station(this, R.drawable.line4_glorieta_colon,
                                      "Glorieta de Coln", -99.153455,19.434588));
        this.stations.add(new Station(this, R.drawable.line4_plaza_republica,
                                      "Plaza de la Repblica", -99.154868,19.436840));
        /**/
    }
}
