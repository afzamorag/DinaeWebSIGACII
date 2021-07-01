/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.util;

import co.gov.policia.dinae.modelo.Carreras;
import co.gov.policia.dinae.modelo.CarrerasPK;
import co.gov.policia.dinae.modelo.NivelesAcademicos;
import co.gov.policia.dinae.siedu.modelo.Categoria;
import co.gov.policia.dinae.siedu.modelo.CategoriaPK;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.constantes.EstadoEvaluacionEnum;
import co.gov.policia.dinae.siedu.modelo.Evaluacion;
import co.gov.policia.dinae.siedu.modelo.Grado;
import co.gov.policia.dinae.siedu.modelo.GradoPK;
import co.gov.policia.dinae.siedu.modelo.PAE;
import co.gov.policia.dinae.siedu.modelo.PreguntaEvaluacion;
import co.gov.policia.dinae.siedu.modelo.SieduEvalGrado;
import co.gov.policia.dinae.siedu.modelo.SieduEvalGradoPK;
import co.gov.policia.dinae.siedu.modelo.SieduEvento;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Juan Jose Buzon
 */
public class EvaluacionUtil {

  public static Evaluacion obtenerEvaluacion(Object[] objects) {
    Evaluacion evaluacion = new Evaluacion();
    evaluacion.setId(((BigDecimal) objects[0]).longValue());
    evaluacion.setEvalDescri((String) objects[1]);
    evaluacion.setEvalNroPreg(((BigDecimal) objects[2]).longValue());
    evaluacion.setEvalFecha((Date) objects[3]);
    evaluacion.setEstadoEvaluacion(EstadoEvaluacionEnum.valueOf(objects[4].toString()));
    evaluacion.setAplicada((String) objects[5]);
    evaluacion.setEvalUsuCrea((String) objects[6]);
    evaluacion.setEvalFechaCrea((Date) objects[7]);
    evaluacion.setEvalMaquinaCrea((String) objects[8]);
    evaluacion.setEvalIpCrea((String) objects[9]);

    evaluacion.setProceso(createProcess(objects));
    evaluacion.setModalidad(crearModalidad(objects));
    evaluacion.setPae(createPAE(objects));
    evaluacion.setEvento(crearEvento(objects));

    evaluacion.setEvalGrados(new ArrayList<SieduEvalGrado>());
//        evaluacion.setEvalCategorias(new ArrayList<SieduEvalCategoria>());
    evaluacion.setPreguntasEvaluacion(new ArrayList<PreguntaEvaluacion>());

    return evaluacion;
  }

  public static SieduEvalGrado obtenerGradoEvaluacion(Object[] objects) {

    SieduEvalGradoPK idEvalGrado = new SieduEvalGradoPK();
    idEvalGrado.setEvgrEval(((BigDecimal) objects[0]).longValue());
    idEvalGrado.setEvgrGradFuerza(((BigDecimal) objects[14]).longValue());
    idEvalGrado.setEvgrGradAlfabetico((String) objects[15]);

    GradoPK idGrado = new GradoPK();
    idGrado.setFuerza(((BigDecimal) objects[14]).longValue());
    idGrado.setAlfabetico((String) objects[15]);

    CategoriaPK idCategoria = new CategoriaPK();
    idCategoria.setFuerza(((BigDecimal) objects[17]).longValue());
    idCategoria.setIdCategoria(((BigDecimal) objects[18]).longValue());

    Categoria categoria = new Categoria();
    categoria.setId(idCategoria);
    categoria.setDescripcion((String) objects[19]);

    Grado grado = new Grado();
    grado.setId(idGrado);
    grado.setDescripcion((String) objects[16]);
    grado.setCategoria(categoria);

    SieduEvalGrado evalGrado = new SieduEvalGrado();
    evalGrado.setId(idEvalGrado);
    evalGrado.setGrado(grado);

    return evalGrado;
  }

  private static Dominio createProcess(Object[] objects) {
    Dominio evalDomProce = new Dominio();
    evalDomProce.setId(((BigDecimal) objects[10]).longValue());
    evalDomProce.setNombre((String) objects[11]);
    return evalDomProce;
  }

  private static Dominio crearModalidad(Object[] objects) {
    Dominio evalDomModal = new Dominio();
    evalDomModal.setId(((BigDecimal) objects[12]).longValue());
    evalDomModal.setNombre((String) objects[13]);
    return evalDomModal;
  }

  private static PAE createPAE(Object[] objects) {
    PAE pae = new PAE();
    pae.setId(((BigDecimal) objects[20]).longValue());
    pae.setVigencia((String) objects[21]);
    return pae;
  }

  private static SieduEvento crearEvento(Object[] objects) {

    NivelesAcademicos nivelAcademico = new NivelesAcademicos();
    nivelAcademico.setIdNivelAcademico(((BigDecimal) objects[26]).longValue());
    nivelAcademico.setDescripcion((String) objects[27]);

    Carreras carrera = new Carreras();
    CarrerasPK pk = new CarrerasPK();
    pk.setIdCarrera(((BigDecimal) objects[23]).longValue());
    pk.setFuerza(((BigDecimal) objects[28]).longValue());
    carrera.setCarrerasPK(pk);
    carrera.setDescripcion((String) objects[24]);
    carrera.setTitulo((String) objects[25]);
    carrera.setNivelAcademico(nivelAcademico);

    SieduEvento evento = new SieduEvento();
    evento.setId(((BigDecimal) objects[22]).longValue());
    evento.setCarrera(carrera);

    return evento;
  }

}
