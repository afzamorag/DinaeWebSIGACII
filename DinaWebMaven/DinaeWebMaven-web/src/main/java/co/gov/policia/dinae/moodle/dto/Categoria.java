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

/**
 * Clase Categoria
 * @author ferney
 *
 */
public class Categoria implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2430507131345065029L;

    private Integer id;
    private String name;
    private String idnumber;
    private String  description;
    private Integer descriptionformat;
    private Integer parent;
    private Integer sortorder;
    private Integer coursecount;
    private Integer visible;
    private Integer visibleold;
    private Date timemodified;
    private Integer depth;
    private String  path;
    private String theme;
    private Integer level;
    
    private List<Categoria> detella = new ArrayList<Categoria>();
    
    /**
     * Instancia la clase sin datos
     */
    public Categoria() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
     * Instancia la calse con valores
     * @param id
     * @param name
     * @param idnumber
     * @param description
     * @param descriptionformat
     * @param parent
     * @param sortorder
     * @param coursecount
     * @param visible
     * @param visibleold
     * @param timemodified
     * @param depth
     * @param path
     * @param theme
     */
    public Categoria(Integer id, String name, String idnumber, String description, Integer descriptionformat,
            Integer parent, Integer sortorder, Integer coursecount, Integer visible, Integer visibleold,
            Date timemodified, Integer depth, String path, String theme) {
        super();
        this.id = id;
        this.name = name;
        this.idnumber = idnumber;
        this.description = description;
        this.descriptionformat = descriptionformat;
        this.parent = parent;
        this.sortorder = sortorder;
        this.coursecount = coursecount;
        this.visible = visible; 
        this.visibleold = visibleold;
        this.timemodified = timemodified;
        this.depth = depth;
        this.path = path;
        this.theme = theme;
    }

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getIdnumber() {
        return idnumber;
    }
    
    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
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
    
    public Integer getParent() {
        return parent;
    }
    
    public void setParent(Integer parent) {
        this.parent = parent;
    }
    
    public Integer getSortorder() {
        return sortorder;
    }
    
    public void setSortorder(Integer sortorder) {
        this.sortorder = sortorder;
    }
    
    public Integer getCoursecount() {
        return coursecount;
    }
    
    public void setCoursecount(Integer coursecount) {
        this.coursecount = coursecount;
    }
    
    public Integer getVisible() {
        return visible;
    }
    
    public void setVisible(Integer visible) {
        this.visible = visible;
    }
    
    public Integer getVisibleold() {
        return visibleold;
    }
    
    public void setVisibleold(Integer visibleold) {
        this.visibleold = visibleold;
    }
    
    public Date getTimemodified() {
        return timemodified;
    }
    
    public void setTimemodified(Date timemodified) {
        this.timemodified = timemodified;
    }
    
    public Integer getDepth() {
        return depth;
    }
    
    public void setDepth(Integer depth) {
        this.depth = depth;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getTheme() {
        return theme;
    }
    
    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Categoria> getDetella() {
        return detella;
    }

    public void setDetella(List<Categoria> detella) {
        this.detella = detella;
    }
}