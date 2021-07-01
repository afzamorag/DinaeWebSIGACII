/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.moodle.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.gov.policia.dinae.moodle.config.Funciones;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase de Curso
 * @author ferney
 *
 */
public class Curso implements Serializable {
	
    /**
     * 
     */
    private static final long serialVersionUID = -8194587608379240043L;

    private Integer id;
    private Integer categoryid;
    private Integer categorysortorder;
    private String fullname;
    private String displayname;
    private String idnumber;
    private String summary;
    private Integer summaryformat;
    private String format;
    private Integer showgrades;
    private Integer newsitems;
    private Date startdate;
    private Date enddate;
    private Integer numsections;
    private Integer maxbytes;
    private Integer showreports;
    private Integer visible;
    private Integer groupmode;
    private Integer groupmodeforce;
    private Integer defaultgroupingid;
    private Date timecreated;
    private Date timemodified;
    private Integer enablecompletion;
    private Integer completionnotify;
    private String lang;
    private String forcetheme;
    private String shortname;
    private Integer hiddensections;
    private List<CursoFormatOption> formatOptions = new ArrayList<CursoFormatOption>();
	
    private Map<String, Integer> alumnos = new HashMap<String, Integer>();
    private Map<String, Integer> docentes = new HashMap<String,Integer>();
	
        
    /**
     *  Instancia la clase sin datos
     */
    public Curso() {
            super();
            // TODO Auto-generated constructor stub
    }

    /**
     * Devuelve la cadena par ala creación del curso 
     * @param sPos
     * @return
     */
    public String getStringPosCreate(String sPos) {
            String sResult = "";
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "fullname", this.fullname, true);
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "shortname", this.shortname, true);
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "categoryid", Funciones.IntegerToString(this.categoryid), true);
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "idnumber", this.idnumber, false);
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "summary", this.summary, false);
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "summaryformat", Funciones.IntegerToString(this.summaryformat), true);
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "format", this.format, true);
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "showgrades", Funciones.IntegerToString(this.showgrades), true);
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "newsitems", Funciones.IntegerToString(this.newsitems), true);
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "startdate", Funciones.DateToString(this.startdate), false);
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "numsections", Funciones.IntegerToString(this.numsections), false);
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "maxbytes", Funciones.IntegerToString(this.maxbytes), true);
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "hiddensections", Funciones.IntegerToString(this.hiddensections), false);
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "groupmode", Funciones.IntegerToString(this.groupmode), true);
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "groupmodeforce", Funciones.IntegerToString(this.groupmodeforce), true);
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "defaultgroupingid", Funciones.IntegerToString(this.defaultgroupingid), true);
            sResult = Funciones.addPostParameter("courses",sResult,sPos, "enablecompletion", Funciones.IntegerToString(this.enablecompletion), false);
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "completionnotify", Funciones.IntegerToString(this.completionnotify), false);
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "lang", this.lang, false);
            sResult = Funciones.addPostParameter("courses",sResult, sPos, "forcetheme", this.forcetheme, false);

            for(Integer i = 0; i < this.formatOptions.size(); i++) {
                    CursoFormatOption cf = this.formatOptions.get(i.intValue());
                    sResult = Funciones.addPostParameter("courses","courseformatoptions",sResult, sPos,i.toString(), "name", cf.getName(), true);
                    sResult = Funciones.addPostParameter("courses","courseformatoptions",sResult, sPos,i.toString(), "value",  Funciones.IntegerToString(cf.getValue()), true);
            }

            return sResult;
    }

    /**
     * Devuelve la cadena para actualizar el curso
     * @param sPos
     * @return
     */
    public String getStringPos(String sPos) {
            String sResult = "";

            sResult = Funciones.addPostParameter("courses",sResult,sPos, "id", Funciones.IntegerToString(this.id), true);
            sResult = Funciones.addPostParameter("courses",sResult,sPos, "categoryid", Funciones.IntegerToString(this.categoryid), true);
            sResult = Funciones.addPostParameter("courses",sResult,sPos, "fullname", this.fullname, true);
            sResult = Funciones.addPostParameter("courses",sResult,sPos, "idnumber", this.idnumber, true);
            //sResult = Funciones.addPostParameter("courses",sResult,sPos, "summary", this.summary, true);
            //sResult = Funciones.addPostParameter("courses",sResult,sPos, "summaryformat", Funciones.IntegerToString(this.summaryformat), true);
            //sResult = Funciones.addPostParameter("courses",sResult,sPos, "format", this.format, true);
            //sResult = Funciones.addPostParameter("courses",sResult,sPos, "showgrades", Funciones.IntegerToString(this.showgrades), true);
            //sResult = Funciones.addPostParameter("courses",sResult,sPos, "newsitems", Funciones.IntegerToString(this.newsitems), true);
            sResult = Funciones.addPostParameter("courses",sResult,sPos, "startdate", Funciones.DateToString(this.startdate), true);
            sResult = Funciones.addPostParameter("courses",sResult,sPos, "enddate", Funciones.DateToString(this.enddate), true);
            //sResult = Funciones.addPostParameter("courses",sResult,sPos, "numsections", Funciones.IntegerToString(this.numsections), false);
            //sResult = Funciones.addPostParameter("courses",sResult,sPos, "maxbytes", Funciones.IntegerToString(this.maxbytes), true);
            //sResult = Funciones.addPostParameter("courses",sResult,sPos, "showreports", Funciones.IntegerToString(this.showreports), true);
            sResult = Funciones.addPostParameter("courses",sResult,sPos, "visible", Funciones.IntegerToString(this.visible), true);
            //sResult = Funciones.addPostParameter("courses",sResult,sPos, "groupmode", Funciones.IntegerToString(this.groupmode), true);
            //sResult = Funciones.addPostParameter("courses",sResult,sPos, "groupmodeforce", Funciones.IntegerToString(this.groupmodeforce), true);
            //sResult = Funciones.addPostParameter("courses",sResult,sPos, "defaultgroupingid", Funciones.IntegerToString(this.defaultgroupingid), true);
            //sResult = Funciones.addPostParameter("courses",sResult,sPos, "enablecompletion", Funciones.IntegerToString(this.enablecompletion), true);
            //sResult = Funciones.addPostParameter("courses",sResult,sPos, "completionnotify", Funciones.IntegerToString(this.completionnotify), true);
            //sResult = Funciones.addPostParameter("courses",sResult,sPos, "forcetheme", this.forcetheme, true);
            sResult = Funciones.addPostParameter("courses",sResult,sPos, "shortname", this.shortname, true);
            //sResult = Funciones.addPostParameter("courses",sResult,sPos, "hiddensections", Funciones.IntegerToString(this.hiddensections), true);
            for(Integer i = 0; i < this.formatOptions.size(); i++) {
                    CursoFormatOption cf = this.formatOptions.get(i.intValue());
                    sResult = Funciones.addPostParameter("courses","courseformatoptions",sResult, sPos,i.toString(), "name", cf.getName(), true);
                    sResult = Funciones.addPostParameter("courses","courseformatoptions",sResult, sPos,i.toString(), "value",  Funciones.IntegerToString(cf.getValue()), true);
            }

            return sResult;
    }
    
    public String getStringDelete() {
        String sResult = "courseids[0]=" + this.id;
        return sResult;
    }
    
    public void limpiarAsignaciones() {
        alumnos = new HashMap<String, Integer>();
        docentes = new HashMap<String,Integer>();        
    }

    public Integer getId() {
            return id;
    }

    public void setId(Integer id) {
            this.id = id;
    }

    public Integer getCategoryid() {
            return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
            this.categoryid = categoryid;
    }

    public Integer getCategorysortorder() {
            return categorysortorder;
    }

    public void setCategorysortorder(Integer categorysortorder) {
            this.categorysortorder = categorysortorder;
    }

    public String getFullname() {
            return fullname;
    }

    public void setFullname(String fullname) {
            this.fullname = fullname;
    }

    public String getDisplayname() {
            return displayname;
    }

    public void setDisplayname(String displayname) {
            this.displayname = displayname;
    }

    public String getIdnumber() {
            return idnumber;
    }

    public void setIdnumber(String idnumber) {
            this.idnumber = idnumber;
    }

    public String getSummary() {
            return summary;
    }

    public void setSummary(String summary) {
            this.summary = summary;
    }

    public Integer getSummaryformat() {
            return summaryformat;
    }

    public void setSummaryformat(Integer summaryformat) {
            this.summaryformat = summaryformat;
    }

    public String getFormat() {
            return format;
    }

    public void setFormat(String format) {
            this.format = format;
    }

    public Integer getShowgrades() {
            return showgrades;
    }

    public void setShowgrades(Integer showgrades) {
            this.showgrades = showgrades;
    }

    public Integer getNewsitems() {
            return newsitems;
    }

    public void setNewsitems(Integer newsitems) {
            this.newsitems = newsitems;
    }

    public Date getStartdate() {
            return startdate;
    }

    public void setStartdate(Date startdate) {
            this.startdate = startdate;
    }

    public Date getEnddate() {
            return enddate;
    }

    public void setEnddate(Date enddate) {
            this.enddate = enddate;
    }

    public Integer getNumsections() {
            return numsections;
    }

    public void setNumsections(Integer numsections) {
            this.numsections = numsections;
    }

    public Integer getMaxbytes() {
            return maxbytes;
    }

    public void setMaxbytes(Integer maxbytes) {
            this.maxbytes = maxbytes;
    }

    public Integer getShowreports() {
            return showreports;
    }

    public void setShowreports(Integer showreports) {
            this.showreports = showreports;
    }

    public Integer getVisible() {
            return visible;
    }

    public void setVisible(Integer visible) {
            this.visible = visible;
    }

    public Integer getGroupmode() {
            return groupmode;
    }

    public void setGroupmode(Integer groupmode) {
            this.groupmode = groupmode;
    }

    public Integer getGroupmodeforce() {
            return groupmodeforce;
    }

    public void setGroupmodeforce(Integer groupmodeforce) {
            this.groupmodeforce = groupmodeforce;
    }

    public Integer getDefaultgroupingid() {
            return defaultgroupingid;
    }

    public void setDefaultgroupingid(Integer defaultgroupingid) {
            this.defaultgroupingid = defaultgroupingid;
    }

    public Date getTimecreated() {
            return timecreated;
    }

    public void setTimecreated(Date timecreated) {
            this.timecreated = timecreated;
    }

    public Date getTimemodified() {
            return timemodified;
    }

    public void setTimemodified(Date timemodified) {
            this.timemodified = timemodified;
    }

    public Integer getEnablecompletion() {
            return enablecompletion;
    }

    public void setEnablecompletion(Integer enablecompletion) {
            this.enablecompletion = enablecompletion;
    }

    public Integer getCompletionnotify() {
            return completionnotify;
    }

    public void setCompletionnotify(Integer completionnotify) {
            this.completionnotify = completionnotify;
    }

    public String getLang() {
            return lang;
    }

    public void setLang(String lang) {
            this.lang = lang;
    }

    public String getForcetheme() {
            return forcetheme;
    }

    public void setForcetheme(String forcetheme) {
            this.forcetheme = forcetheme;
    }

    public List<CursoFormatOption> getFormatOptions() {
            return formatOptions;
    }

    public void setFormatOptions(List<CursoFormatOption> formatOptions) {
            this.formatOptions = formatOptions;
    }

    public String getShortname() {
            return shortname;
    }

    public void setShortname(String shortname) {
            this.shortname = shortname;
    }

    public Integer getHiddensections() {
            return hiddensections;
    }

    public void setHiddensections(Integer hiddensections) {
            this.hiddensections = hiddensections;
    }
    
    public Map<String, Integer> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(Map<String, Integer> alumnos) {
        this.alumnos = alumnos;
    }

    public Map<String, Integer> getDocentes() {
        return docentes;
    }

    public void setDocentes(Map<String, Integer> docentes) {
        this.docentes = docentes;
    }
}
