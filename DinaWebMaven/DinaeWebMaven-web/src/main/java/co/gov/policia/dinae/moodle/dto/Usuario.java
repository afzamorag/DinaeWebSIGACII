/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.moodle.dto;

import java.io.Serializable;

import co.gov.policia.dinae.moodle.config.Funciones;

/**
 * Clase de Usuarios
 *
 * @author ferney
 *
 */
public class Usuario implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2679948661289155937L;

    private Integer id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String fullname;
    private String email;
    private String department;
    private Integer firstaccess;
    private Integer lastaccess;
    private String auth;
    private Boolean suspended;
    private Boolean confirmed;
    private String lang;
    private String theme;
    private String timezone;
    private Integer mailformat;
    private String description;
    private Integer descriptionformat;
    private String city;
    private String country;
    private String profileimageurlsmall;
    private String profileimageurl;
    private String idnumber;    
    private String institution;
    private String middlename;

    /**
     * Instancia la clase sin datos
	 *
     */
    public Usuario() {
        super();
        // TODO Auto-generated constructor stub
    }
/**
 * 
 * @param id
 * @param username
 * @param password
 * @param firstname
 * @param lastname
 * @param fullname
 * @param email
 * @param department
 * @param firstaccess
 * @param lastaccess
 * @param auth
 * @param suspended
 * @param confirmed
 * @param lang
 * @param theme
 * @param timezone
 * @param mailformat
 * @param description
 * @param descriptionformat
 * @param city
 * @param country
 * @param profileimageurlsmall
 * @param profileimageurl
 * @param idnumber
 * @param institution
 * @param middlename 
 */
    public Usuario(Integer id, String username, String password, String firstname, String lastname, String fullname, String email, String department, Integer firstaccess, Integer lastaccess, String auth, Boolean suspended, Boolean confirmed, String lang, String theme, String timezone, Integer mailformat, String description, Integer descriptionformat, String city, String country, String profileimageurlsmall, String profileimageurl, String idnumber, String institution, String middlename) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.fullname = fullname;
        this.email = email;
        this.department = department;
        this.firstaccess = firstaccess;
        this.lastaccess = lastaccess;
        this.auth = auth;
        this.suspended = suspended;
        this.confirmed = confirmed;
        this.lang = lang;
        this.theme = theme;
        this.timezone = timezone;
        this.mailformat = mailformat;
        this.description = description;
        this.descriptionformat = descriptionformat;
        this.city = city;
        this.country = country;
        this.profileimageurlsmall = profileimageurlsmall;
        this.profileimageurl = profileimageurl;
        this.idnumber = idnumber;
        this.institution = institution;
        this.middlename = middlename;
    }


    /**
     * Devuelve una cadena para crear el usuario en moodle
     *
     * @param sPos
     * @return
     */
    public String getStringPosCreate(String sPos) {
        String sResult = "";
        sResult = Funciones.addPostParameter("users", sResult, sPos, "username", this.username, true);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "password", this.password, true);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "firstname", this.firstname, true);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "lastname", this.lastname, true);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "email", this.email, true);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "auth", this.auth, true);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "lang", this.lang, false);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "theme", this.theme, true);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "timezone", this.timezone, true);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "mailformat", Funciones.IntegerToString(this.mailformat), true);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "description", this.description, true);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "idnumber", this.idnumber, true);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "firstnamephonetic", this.department, false);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "lastnamephonetic", this.institution, false);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "middlename", this.middlename, false);
        return sResult;
    }
    
    public String validarDatos() {
        String sOK = "OK";
        
        if(this.username == null || this.username.trim().isEmpty()) {
            sOK = "El username está vacio";
        } else if(this.firstname == null || this.firstname.trim().isEmpty()) {
            sOK = "El nombre está vacio";
        } else if(this.lastname == null || this.lastname.trim().isEmpty()) {
            sOK = "El apellido está vacio";
        } else if(this.email == null || this.email.trim().isEmpty()) {
            sOK = "El email está vacio";
        } else if(this.idnumber == null || this.idnumber.trim().isEmpty()) {
            sOK = "El documento está vacio";
        }
        return sOK;
    }
    
    public String getStringPosUpdate(String sPos) {
        String sResult = "";
        sResult = Funciones.addPostParameter("users", sResult, sPos, "id", this.id.toString(), true);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "username", this.username, true);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "auth", "ldap", true);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "firstnamephonetic", this.department, false);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "lastnamephonetic", this.institution, false);
        sResult = Funciones.addPostParameter("users", sResult, sPos, "middlename", this.middlename, false);
        return sResult;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getFirstaccess() {
        return firstaccess;
    }

    public void setFirstaccess(Integer firstaccess) {
        this.firstaccess = firstaccess;
    }

    public Integer getLastaccess() {
        return lastaccess;
    }

    public void setLastaccess(Integer lastaccess) {
        this.lastaccess = lastaccess;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getMailformat() {
        return mailformat;
    }

    public void setMailformat(Integer mailformat) {
        this.mailformat = mailformat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDescriptionformat() {
        return descriptionformat;
    }

    public void setDescriptionformat(Integer descriptionformat) {
        this.descriptionformat = descriptionformat;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProfileimageurlsmall() {
        return profileimageurlsmall;
    }

    public void setProfileimageurlsmall(String profileimageurlsmall) {
        this.profileimageurlsmall = profileimageurlsmall;
    }

    public String getProfileimageurl() {
        return profileimageurl;
    }

    public void setProfileimageurl(String profileimageurl) {
        this.profileimageurl = profileimageurl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

}
