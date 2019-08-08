package feedback.mpnsc;

/**
 * Created by swatiG on 27-05-2015.
 */
public class DataHolderClass {

    private static DataHolderClass dataObject = null;

    String str_division_spinner;
    String str_subdivision;
    String str_section;
    String str_cycle;
    String str_route;
    String str_ownerships;
    String str_cons_type;
    String str_department;
    String str_ed_exception;
    String str_meter_status;

    String str_seal1;
    String str_seal2;
    String str_seal3;
    String str_seal4;
    String str_seal5;

    String str_adres1;
    String str_adres2;
    String str_adres3;

    String str_billadres1;
    String str_billadres2;
    String str_billadres3;

    String str_ticket_no;
    String str_aadhar_no;
    String feasibility_tariff_load;
    String feasibility_tariff_load_b;

    public String getFeasibility_tariff_load_b() {
        return feasibility_tariff_load_b;
    }

    public void setFeasibility_tariff_load_b(String feasibility_tariff_load_b) {
        this.feasibility_tariff_load_b = feasibility_tariff_load_b;
    }

    public String getFeasibility_tariff_load() {
        return feasibility_tariff_load;
    }

    public void setFeasibility_tariff_load(String feasibility_tariff_load) {
        this.feasibility_tariff_load = feasibility_tariff_load;
    }

    public String getStr_aadhar_no() {
        return str_aadhar_no;
    }

    public void setStr_aadhar_no(String str_aadhar_no) {
        this.str_aadhar_no = str_aadhar_no;
    }

    private DataHolderClass() {
        // left blank intentionally
    }

    public static DataHolderClass getInstance() {
        if (dataObject == null)
            dataObject = new DataHolderClass();
        return dataObject;
    }

    public String get_ticket_no() {
        return str_ticket_no;
    }

    public void set_ticket_no(String ticket_no) {
        this.str_ticket_no = ticket_no;
    }

    //..... for tab1..........//
    public String get_division() {
        return str_division_spinner;
    }

    public void set_division(String division) {
        this.str_division_spinner = division;
    }

    public String get_subdivion() {
        return str_subdivision;
    }

    public void set_subdivision(String subdivision) {
        this.str_subdivision = subdivision;
    }

    public String get_section() {
        return str_section;
    }

    public void set_section(String section) {
        this.str_section = section;
    }

    public String get_cycle() {
        return str_cycle;
    }

    public void set_cycle(String cycle) {
        this.str_cycle = cycle;
    }

    public String get_route() {
        return str_route;
    }

    public void set_route(String route) {
        this.str_route = route;
    }

    public String get_ownerships() {
        return str_ownerships;
    }

    public void set_ownerships(String ownerships) {
        this.str_ownerships = ownerships;
    }

    public String get_cons_type() {
        return str_cons_type;
    }

    public void set_cons_type(String cons_type) {
        this.str_cons_type = cons_type;
    }

    public String get_department() {
        return str_department;
    }

    public void set_department(String department) {
        this.str_department = department;
    }

    public String get_ed_exception() {
        return str_ed_exception;
    }

    public void set_ed_exception(String ed_exception) {
        this.str_ed_exception = ed_exception;
    }

    public String get_meter_status() {
        return str_meter_status;
    }

    public void set_meter_status(String meter_status) {
        this.str_meter_status = meter_status;
    }

    //....for tab3....//
    String str_supply_type;
    String str_supply_level;
    String str_load_unit;
    String str_metering_side;
    String str_trans_ownership;
    String str_trans_capacity;
    String str_tariff_cat;
    String str_bill_demand;
    String str_scheme;
    String str_connection;
    String str_connect_load;
    String str_other;
   String feeder_name;

    String str_transformer_capacity;
    String str_transformer_code;
    String pole_number;
    String mru_number;
    String consumer_image;
    String land_image;
    String address_image;
    String aadhar_image;
    String consumer_image_name;
    String land_image_name;

    public String getConsumer_image_name() {
        return consumer_image_name;
    }

    public void setConsumer_image_name(String consumer_image_name) {
        this.consumer_image_name = consumer_image_name;
    }

    public String getLand_image_name() {
        return land_image_name;
    }

    public void setLand_image_name(String land_image_name) {
        this.land_image_name = land_image_name;
    }

    public String getAddress_image_name() {
        return address_image_name;
    }

    public void setAddress_image_name(String address_image_name) {
        this.address_image_name = address_image_name;
    }

    public String getAadhar_image_name() {
        return aadhar_image_name;
    }

    public void setAadhar_image_name(String aadhar_image_name) {
        this.aadhar_image_name = aadhar_image_name;
    }

    String address_image_name;
    String aadhar_image_name;

    public String getPole_number() {
        return pole_number;
    }

    public void setPole_number(String pole_number) {
        this.pole_number = pole_number;
    }

    public String getMru_number() {
        return mru_number;
    }

    public void setMru_number(String mru_number) {
        this.mru_number = mru_number;
    }

    public String getConsumer_image() {
        return consumer_image;
    }

    public void setConsumer_image(String consumer_image) {
        this.consumer_image = consumer_image;
    }

    public String getLand_image() {
        return land_image;
    }

    public void setLand_image(String land_image) {
        this.land_image = land_image;
    }

    public String getAddress_image() {
        return address_image;
    }

    public void setAddress_image(String address_image) {
        this.address_image = address_image;
    }

    public String getAadhar_image() {
        return aadhar_image;
    }

    public void setAadhar_image(String aadhar_image) {
        this.aadhar_image = aadhar_image;
    }

    public String get_supply_type() {
        return str_supply_type;
    }

    public void set_supply_type(String supply_type) {
        this.str_supply_type = supply_type;
    }

    public String get_supply_level() {
        return str_supply_level;
    }

    public void set_supply_level(String supply_level) {
        this.str_supply_level = supply_level;
    }

    public String get_load_unit() {
        return str_load_unit;
    }

    public void set_load_unit(String load_unit) {
        this.str_load_unit = load_unit;
    }

    public String get_metering_side() {
        return str_metering_side;
    }

    public void set_metering_side(String metering_side) {
        this.str_metering_side = metering_side;
    }

    public String get_trans_ownership() {
        return str_trans_ownership;
    }

    public void set_trans_ownership(String trans_ownership) {
        this.str_trans_ownership = trans_ownership;
    }

    public String get_trans_capacity() {
        return str_trans_capacity;
    }

    public void set_trans_capacity(String trans_capacity) {
        this.str_trans_capacity = trans_capacity;
    }

    public String get_tariff_cat() {
        return str_tariff_cat;
    }

    public void set_tariff_cat(String tariff_cat) {
        this.str_tariff_cat = tariff_cat;
    }

    public String get_bill_demand() {
        return str_bill_demand;
    }

    public void set_bill_demand(String bill_demand) {
        this.str_bill_demand = bill_demand;
    }

    public String get_scheme() {
        return str_scheme;
    }

    public void set_scheme(String scheme) {
        this.str_scheme = scheme;
    }

    public String get_connection() {
        return str_connection;
    }

    public void set_connection(String connection) {
        this.str_connection = connection;
    }

    //...edittext....//

    public String get_connect_load() {
        return str_connect_load;
    }

    public void set_connect_load(String connect_load) {
        this.str_connect_load = connect_load;
    }

    public String get_other() {
        return str_other;
    }

    public void set_other(String other) {
        this.str_other = other;
    }

    //.....tab 2....//

    String str_title, str_name, str_father_name, str_title_org, str_designation, str_type_org,
            str_name_org, str_name_org_corp;

    boolean boolean_value = true;

    public boolean get_boolean_value() {
        return boolean_value;
    }

    public void set_boolean_value(boolean boolean_value) {
        this.boolean_value = boolean_value;
    }

    public String get_title() {
        return str_title;
    }

    public void set_title(String title) {
        this.str_title = title;
    }

    public String get_name() {
        return str_name;
    }

    public void set_name(String name) {
        this.str_name = name;
    }

    public String get_father_name() {
        return str_father_name;
    }

    public void set_father_name(String father_name) {
        this.str_father_name = father_name;
    }

    public String get_title_org() {
        return str_title_org;
    }

    public void set_title_org(String title_org) {
        this.str_title_org = title_org;
    }

    public String get_name_org() {
        return str_name_org;
    }

    public void set_name_org(String name_org) {
        this.str_name_org = name_org;
    }

    public String get_designation() {
        return str_designation;
    }

    public void set_designation(String designation) {
        this.str_designation = designation;
    }

    public String get_type_org() {
        return str_type_org;
    }

    public void set_type_org(String type_org) {
        this.str_type_org = type_org;
    }

    public String get_name_org_corp() {
        return str_name_org_corp;
    }

    public void set_name_org_corp(String name_org_corp) {
        this.str_name_org_corp = name_org_corp;
    }

    //......tab2 current detail........//
    String str_house_no;
    String str_bulding_no;
    String str_street;
    String str_city;
    String str_district;
    String str_tehsil;
    String str_block;
    String str_gp;
    String str_village;
    String str_pin_no;
    String str_email;
    String str_mobile;
    String str_landline;
    String str_pan_no;
    String str_pan1_no;
    private String str_date;

    String department_type;
    String no_bill;
    String electrical_address;
    String str_ed_percentage;

    String radio_consumert;
    String radio_goveremntt;
    String radio_billingt;
    String radio_edleviedt;

    boolean str_same_add;

    public void setStr_adres1(String str_adres1) {
        this.str_adres1 = str_adres1;
    }

    public void setStr_adres2(String str_adres2) {
        this.str_adres2 = str_adres2;
    }

    public void setStr_adres3(String str_adres3) {
        this.str_adres3 = str_adres3;
    }

    public String getStr_adres1() {
        return str_adres1;
    }

    public String getStr_adres2() {
        return str_adres2;
    }

    public String getStr_adres3() {
        return str_adres3;
    }
//    public void setStr_billadres1(String str_billadres1) {
//        this.str_billadres1 = str_billadres1;
//    }
//
//    public void setStr_adres2(String str_adres2) {
//        this.str_adres2 = str_adres2;
//    }
//
//    public void setStr_adres3(String str_adres3) {
//        this.str_adres3 = str_adres3;
//    }
//
//    public String getStr_adres1() {
//        return str_adres1;
//    }
//
//    public String getStr_adres2() {
//        return str_adres2;
//    }
//
//    public String getStr_adres3() {
//        return str_adres3;
//    }

    public void setStr_seal1(String str_seal1) {
        this.str_seal1 = str_seal1;
    }

    public void setStr_seal2(String str_seal2) {
        this.str_seal2 = str_seal2;
    }

    public void setStr_seal3(String str_seal3) {
        this.str_seal3 = str_seal3;
    }

    public String getStr_seal1() {
        return str_seal1;
    }

    public String getStr_seal2() {
        return str_seal2;
    }

    public String getStr_seal3() {
        return str_seal3;
    }

    public void set_same_add(boolean same_add) {
        this.str_same_add = same_add;
    }

    public boolean get_same_add() {
        return str_same_add;
    }

    public String get_house_no() {
        return str_house_no;
    }

    public void set_house_no(String house_no) {
        this.str_house_no = house_no;
    }

    public String get_bulding_no() {
        return str_bulding_no;
    }

    public void set_bulding_no(String bulding_no) {
        this.str_bulding_no = bulding_no;
    }

    public String get_street() {
        return str_street;
    }

    public void set_street(String street) {
        this.str_street = street;
    }

    public String get_city() {
        return str_city;
    }

    public void set_city(String city) {
        this.str_city = city;
    }

    public String get_district() {
        return str_district;
    }

    public void set_district(String district) {
        this.str_district = district;
    }

    public String get_tehsil() {
        return str_tehsil;
    }

    public void set_tehsil(String tehsil) {
        this.str_tehsil = tehsil;
    }

    public String get_block() {
        return str_block;
    }

    public void set_block(String block) {
        this.str_block = block;
    }

    public String get_gp() {
        return str_gp;
    }

    public void set_gp(String gp) {
        this.str_gp = gp;
    }

    public String get_village() {
        return str_village;
    }

    public void set_village(String village) {
        this.str_village = village;
    }

    public String get_pin_no() {
        return str_pin_no;
    }

    public void set_pin_no(String pin_no) {
        this.str_pin_no = pin_no;
    }

    public String get_email() {
        return str_email;
    }

    public void set_email(String email) {
        this.str_email = email;
    }

    public String get_mobile() {
        return str_mobile;
    }

    public void set_mobile(String mobile) {
        this.str_mobile = mobile;
    }

    public String get_landline() {
        return str_landline;
    }

    public void set_landline(String landline) {
        this.str_landline = landline;
    }

    public String get_pan_no() {
        return str_pan_no;
    }

    public void set_pan_no(String pan_no) {
        this.str_pan_no = pan_no;
    }

    //...tab2.... billing detail.....//
    String str_house_no1, str_bulding_no1, str_street1, str_city1, str_district1, str_tehsil1, str_block1,
            str_gp1, str_village1, str_pin_no1, str_email1,
            str_mobile1, str_landline1, str_pan_no1;

    public String get_house_no1() {
        return str_house_no1;
    }

    public void set_house_no1(String house_no1) {
        this.str_house_no1 = house_no1;
    }

    public String get_bulding_no1() {
        return str_bulding_no1;
    }

    public void set_bulding_no1(String bulding_no1) {
        this.str_bulding_no1 = bulding_no1;
    }

    public String get_street1() {
        return str_street1;
    }

    public void set_street1(String street1) {
        this.str_street1 = street1;
    }

    public String get_city1() {
        return str_city1;
    }

    public void set_city1(String city1) {
        this.str_city1 = city1;
    }

    public String get_district1() {
        return str_district1;
    }

    public void set_district1(String district1) {
        this.str_district1 = district1;
    }

    public String get_tehsil1() {
        return str_tehsil1;
    }

    public void set_tehsil1(String tehsil1) {
        this.str_tehsil1 = tehsil1;
    }

    public String get_block1() {
        return str_block1;
    }

    public void set_block1(String block1) {
        this.str_block1 = block1;
    }

    public String get_gp1() {
        return str_gp1;
    }

    public void set_gp1(String gp1) {
        this.str_gp1 = gp1;
    }

    public String get_village1() {
        return str_village1;
    }

    public void set_village1(String village1) {
        this.str_village1 = village1;
    }

    public String get_pin_no1() {
        return str_pin_no1;
    }

    public void set_pin_no1(String pin_no1) {
        this.str_pin_no1 = pin_no1;
    }

    public String get_email1() {
        return str_email1;
    }

    public void set_email1(String email1) {
        this.str_email1 = email1;
    }

    public String get_mobile1() {
        return str_mobile1;
    }

    public void set_mobile1(String mobile1) {
        this.str_mobile1 = mobile1;
    }

    public String get_landline1() {
        return str_landline1;
    }

    public void set_landline1(String landline1) {
        this.str_landline1 = landline1;
    }

    public String get_pan_no1() {
        return str_pan_no1;
    }

    public void set_pan_no1(String pan_no1) {
        this.str_pan_no1 = pan_no1;
    }
    //...........

    String str_person_available = "available";

    public String get_person_available() {
        return str_person_available;
    }

    public void set_person_available(String person_available) {
        this.str_person_available = person_available;
    }

    //...............new meter...............//

    String str_new_meter_ticket_no;
    String str_new_meter_cons_name;
    String str_new_meter_cons_add;
    String str_new_meter_cons_division;
    String str_new_meter_cons_subdivision;
    String str_new_meter_cons_route;
    String str_new_meter_cons_section;

    public String getStr_new_meter_cons_Add() {
        return str_new_meter_cons_Add;
    }

    public void setStr_new_meter_cons_Add(String str_new_meter_cons_Add) {
        this.str_new_meter_cons_Add = str_new_meter_cons_Add;
    }

    String str_new_meter_cons_Add;

    public String get_new_meter_cons_name() {
        return str_new_meter_cons_name;
    }

    public void set_new_meter_cons_name(String new_meter_cons_name) {
        this.str_new_meter_cons_name = new_meter_cons_name;
    }

    public String get_new_meter_cons_add() {
        return str_new_meter_cons_add;
    }

    public void set_new_meter_cons_add(String new_meter_cons_name) {
        this.str_new_meter_cons_add = new_meter_cons_name;
    }

    public String get_new_meter_ticket_no() {
        return str_new_meter_ticket_no;
    }

    public void set_new_meter_ticket_no(String new_meter_ticket_no) {
        this.str_new_meter_ticket_no = new_meter_ticket_no;
    }

    String str_lat;
    String str_long;

    public String getSeal3() {
        return seal3;
    }

    public void setSeal3(String seal3) {
        this.seal3 = seal3;
    }

    public String getSeal2() {
        return seal2;
    }

    public void setSeal2(String seal2) {
        this.seal2 = seal2;
    }

    public String getSeal1() {
        return seal1;
    }

    public void setSeal1(String seal1) {
        this.seal1 = seal1;
    }

    public String getMetrdigits() {
        return metrdigits;
    }

    public void setMetrdigits(String metrdigits) {
        this.metrdigits = metrdigits;
    }

    public String getMetrowner() {
        return metrowner;
    }

    public void setMetrowner(String metrowner) {
        this.metrowner = metrowner;
    }

    public String getBilbasis() {
        return bilbasis;
    }

    public void setBilbasis(String bilbasis) {
        this.bilbasis = bilbasis;
    }

    public String getMigir() {
        return migir;
    }

    public void setMigir(String migir) {
        this.migir = migir;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getDevbrandtyp() {
        return devbrandtyp;
    }

    public void setDevbrandtyp(String devbrandtyp) {
        this.devbrandtyp = devbrandtyp;
    }

    public String getDevbrand() {
        return devbrand;
    }

    public void setDevbrand(String devbrand) {
        this.devbrand = devbrand;
    }

    String meterserialno;
    String devbrand;
    String devbrandtyp;
    String phase;
    String migir;
    String bilbasis;
    String metrowner;
    String metrdigits;
    String seal1;
    String seal2;
    String seal3;

    public String getMeterserialno() {
        return meterserialno;
    }

    public void setMeterserialno(String meterserialno) {
        this.meterserialno = meterserialno;
    }

    public String get_lat() {
        return str_lat;
    }

    public void set_lat(String lat) {
        this.str_lat = lat;
    }

    public String get_long() {
        return str_long;
    }

    public void set_long(String lonng) {
        this.str_long = lonng;
    }

    public String get_new_meter_division() {
        return str_new_meter_cons_division;
    }

    public void set_new_meter_cons_division(String new_meter_cons_division) {
        this.str_new_meter_cons_division = new_meter_cons_division;
    }

    public String get_new_meter_cons_subdivision() {
        return str_new_meter_cons_subdivision;
    }

    public void set_new_meter_cons_subdivision(String new_meter_cons_subdivision) {
        this.str_new_meter_cons_subdivision = new_meter_cons_subdivision;
    }

    public String get_new_meter_cons_section() {
        return str_new_meter_cons_section;
    }

    public void set_new_meter_cons_section(String new_meter_cons_section) {
        this.str_new_meter_cons_section = new_meter_cons_section;
    }

    public String get_new_meter_cons_route() {
        return str_new_meter_cons_route;
    }

    public void set_new_meter_cons_route(String new_meter_cons_route) {
        this.str_new_meter_cons_route = new_meter_cons_route;
    }

    // feasibilty value

    String str_feasibility_division, str_feasibility_subdivision, str_feasibility_section;

    public String get_feasibility_division() {
        return str_feasibility_division;
    }

    public void set_feasibility_division(String feasibility_division) {
        this.str_feasibility_division = feasibility_division;
    }

    public String get_feasibility_subdivision() {
        return str_feasibility_subdivision;
    }

    public void set_feasibility_subdivision(String feasibility_subdivision) {
        this.str_feasibility_subdivision = feasibility_subdivision;
    }

    public String get_feasibility_section() {
        return str_feasibility_section;
    }

    public void set_feasibility_section(String feasibility_section) {
        this.str_feasibility_section = feasibility_section;
    }

    // nitin
    public String getStr_transformer_capacity() {
        return str_transformer_capacity;
    }

    public void setStr_transformer_capacity(String str_transformer_capacity) {
        this.str_transformer_capacity = str_transformer_capacity;
    }

    public String getStr_transformer_code() {
        return str_transformer_code;
    }

    public void setStr_transformer_code(String str_transformer_code) {
        this.str_transformer_code = str_transformer_code;
    }

    public String getRadio_consumert() {
        return radio_consumert;
    }

    public void setRadio_consumert(String radio_consumert) {
        this.radio_consumert = radio_consumert;
    }

    public String getDepartment_type() {
        return department_type;
    }

    public void setDepartment_type(String department_type) {
        this.department_type = department_type;
    }

    public String getNo_bill() {
        return no_bill;
    }

    public void setNo_bill(String no_bill) {
        this.no_bill = no_bill;
    }

    public String getElectrical_address() {
        return electrical_address;
    }

    public void setElectrical_address(String electrical_address) {
        this.electrical_address = electrical_address;
    }

    public String getStr_ed_percentage() {
        return str_ed_percentage;
    }

    public void setStr_ed_percentage(String str_ed_percentage) {
        this.str_ed_percentage = str_ed_percentage;
    }

    public String getRadio_goveremntt() {
        return radio_goveremntt;
    }

    public void setRadio_goveremntt(String radio_goveremntt) {
        this.radio_goveremntt = radio_goveremntt;
    }

    public String getRadio_billingt() {
        return radio_billingt;
    }

    public void setRadio_billingt(String radio_billingt) {
        this.radio_billingt = radio_billingt;
    }

    public String getRadio_edleviedt() {
        return radio_edleviedt;
    }

    public void setRadio_edleviedt(String radio_edleviedt) {
        this.radio_edleviedt = radio_edleviedt;
    }

    public String getFeeder_name() {
        return feeder_name;
    }

    public void setFeeder_name(String feeder_name) {
        this.feeder_name = feeder_name;
    }

    public String getStr_date() {
        return str_date;
    }

    public void setStr_date(String str_date) {
        this.str_date = str_date;
    }


    String ticket_no;

    public String getTicket_no() {
        return ticket_no;
    }

    public void setTicket_no(String ticket_no) {
        this.ticket_no = ticket_no;
    }

    public String getHome_lat() {
        return home_lat;
    }

    public void setHome_lat(String home_lat) {
        this.home_lat = home_lat;
    }

    public String getHome_long() {
        return home_long;
    }

    public void setHome_long(String home_long) {
        this.home_long = home_long;
    }

    public String getPole_lat() {
        return pole_lat;
    }

    public void setPole_lat(String pole_lat) {
        this.pole_lat = pole_lat;
    }

    public String getPole_long() {
        return pole_long;
    }

    public void setPole_long(String pole_long) {
        this.pole_long = pole_long;
    }

    public String getValue_feasibility() {
        return value_feasibility;
    }

    public void setValue_feasibility(String value_feasibility) {
        this.value_feasibility = value_feasibility;
    }

    public String getStr_manual_fes() {
        return str_manual_fes;
    }

    public void setStr_manual_fes(String str_manual_fes) {
        this.str_manual_fes = str_manual_fes;
    }

    String home_lat;
    String home_long;
    String pole_lat;
    String pole_long;
    String value_feasibility;
    String str_manual_fes;


    String str_first_name,str_middle_name,str_last_name,reg_temp,landmark,khata_no,ward_no,housename,house_flatno,
            adjacent_cons_no,adjacent_mru_no,radio_wiring_status,radio_adjacent_cons,radio_landrecord_type,
            str_spinner_address_proof,str_meterinsimage,str_meterinsimageName;

    public String getStr_meterinsimageName() {
        return str_meterinsimageName;
    }

    public void setStr_meterinsimageName(String str_meterinsimageName) {
        this.str_meterinsimageName = str_meterinsimageName;
    }

    public String getStr_meterinsimage() {

        return str_meterinsimage;
    }

    public void setStr_meterinsimage(String str_meterinsimage) {
        this.str_meterinsimage = str_meterinsimage;
    }


    public String getRadio_adjacent_cons() {
        return radio_adjacent_cons;
    }

    public String getStr_spinner_address_proof() {
        return str_spinner_address_proof;
    }

    public void setStr_spinner_address_proof(String str_spinner_address_proof) {
        this.str_spinner_address_proof = str_spinner_address_proof;
    }

    public void setRadio_adjacent_cons(String radio_adjacent_cons) {
        this.radio_adjacent_cons = radio_adjacent_cons;
    }

    public String getStr_first_name() {
        return str_first_name;
    }

    public String getReg_temp() {
        return reg_temp;
    }

    public String getRadio_wiring_status() {
        return radio_wiring_status;
    }

    public void setRadio_wiring_status(String radio_wiring_status) {
        this.radio_wiring_status = radio_wiring_status;
    }

    public String getAdjacent_cons_no() {

        return adjacent_cons_no;
    }

    public void setAdjacent_cons_no(String adjacent_cons_no) {
        this.adjacent_cons_no = adjacent_cons_no;
    }

    public String getAdjacent_mru_no() {
        return adjacent_mru_no;
    }

    public void setAdjacent_mru_no(String adjacent_mru_no) {
        this.adjacent_mru_no = adjacent_mru_no;
    }

    public String getWard_no() {
        return ward_no;
    }

    public void setWard_no(String ward_no) {
        this.ward_no = ward_no;
    }

    public void setReg_temp(String reg_temp) {
        this.reg_temp = reg_temp;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getKhata_no() {
        return khata_no;
    }

    public void setKhata_no(String khata_no) {
        this.khata_no = khata_no;
    }

    public String getHousename() {
        return housename;
    }

    public void setHousename(String housename) {
        this.housename = housename;
    }

    public String getHouse_flatno() {
        return house_flatno;
    }

    public void setHouse_flatno(String house_flatno) {
        this.house_flatno = house_flatno;
    }

    public void setStr_first_name(String str_first_name) {

        this.str_first_name = str_first_name;
    }

    public String getStr_middle_name() {
        return str_middle_name;
    }

    public void setStr_middle_name(String str_middle_name) {
        this.str_middle_name = str_middle_name;
    }

    public String getStr_last_name() {
        return str_last_name;
    }

    public String getRadio_landrecord_type() {
        return radio_landrecord_type;
    }

    public void setRadio_landrecord_type(String radio_landrecord_type) {
        this.radio_landrecord_type = radio_landrecord_type;
    }

    public void setStr_last_name(String str_last_name) {
        this.str_last_name = str_last_name;
    }


    String str_meter_no;

    String str_reading;

    public String getStr_meter_no() {
        return str_meter_no;
    }

    public void setStr_meter_no(String str_meter_no) {
        this.str_meter_no = str_meter_no;
    }

    public String getStr_reading() {
        return str_reading;
    }

    public void setStr_reading(String str_reading) {
        this.str_reading = str_reading;
    }

    public String getStr_seal_number() {
        return str_seal_number;
    }

    public void setStr_seal_number(String str_seal_number) {
        this.str_seal_number = str_seal_number;
    }

    public String getStr_meter_type() {
        return str_meter_type;
    }

    public void setStr_meter_type(String str_meter_type) {
        this.str_meter_type = str_meter_type;
    }

    public String getStr_meter_phase() {
        return str_meter_phase;
    }

    public void setStr_meter_phase(String str_meter_phase) {
        this.str_meter_phase = str_meter_phase;
    }

    public String getStr_meter_digit() {
        return str_meter_digit;
    }

    public void setStr_meter_digit(String str_meter_digit) {
        this.str_meter_digit = str_meter_digit;
    }

    public String getStr_meter_make() {
        return str_meter_make;
    }

    public void setStr_meter_make(String str_meter_make) {
        this.str_meter_make = str_meter_make;
    }

    String str_seal_number;
    String str_meter_type;
    String str_meter_phase;
    String str_meter_digit;
    String str_meter_make;
    String str_consumer_number;

    String radio_reg_temp;
    String remark_feasibility;

    String nodues_image_name;
    String nodues_image;
    String land_record_type;
    String adress_proof_name_input;

    public String getAdress_proof_name_input() {
        return adress_proof_name_input;
    }

    public void setAdress_proof_name_input(String adress_proof_name_input) {
        this.adress_proof_name_input = adress_proof_name_input;
    }

    public String getLand_record_type() {
        return land_record_type;
    }

    public void setLand_record_type(String land_record_type) {
        this.land_record_type = land_record_type;
    }

    public String getNodues_image_name() {
        return nodues_image_name;
    }

    public void setNodues_image_name(String nodues_image_name) {
        this.nodues_image_name = nodues_image_name;
    }

    public String getNodues_image() {
        return nodues_image;
    }

    public void setNodues_image(String nodues_image) {
        this.nodues_image = nodues_image;
    }

    public String getRemark_feasibility() {
        return remark_feasibility;
    }

    public void setRemark_feasibility(String remark_feasibility) {
        this.remark_feasibility = remark_feasibility;
    }

    public String getRadio_reg_temp() {
        return radio_reg_temp;
    }

    public void setRadio_reg_temp(String radio_reg_temp) {
        this.radio_reg_temp = radio_reg_temp;
    }

    public String getStr_consumer_number() {
        return str_consumer_number;
    }

    public void setStr_consumer_number(String str_consumer_number) {
        this.str_consumer_number = str_consumer_number;
    }


    public String getStr_seal4() {
        return str_seal4;
    }

    public void setStr_seal4(String str_seal4) {
        this.str_seal4 = str_seal4;
    }

    public String getStr_seal5() {
        return str_seal5;
    }

    public void setStr_seal5(String str_seal5) {
        this.str_seal5 = str_seal5;
    }

    public void nullify_DataHolder_feasibility() {

        dataObject = null;

        consumer_image_name = null;
        aadhar_image_name = null;
        str_aadhar_no = null;
        address_image_name = null;
        str_spinner_address_proof = null;
        adress_proof_name_input = null;
        land_image_name = null;
        land_record_type = null;
        nodues_image_name = null;



    }


}
