/*
 * SoftStudio
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.modelo.Carreras;
import co.gov.policia.dinae.modelo.LugarGeografico;
import co.gov.policia.dinae.modelo.NivelesAcademicos;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.modelo.UnidadDependenciaPK;
import co.gov.policia.dinae.siedu.dto.PerfilDependenciaDTO;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Categoria;
import co.gov.policia.dinae.siedu.modelo.CategoriaPK;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.DominioTipo;
import co.gov.policia.dinae.siedu.modelo.Grado;
import co.gov.policia.dinae.siedu.modelo.Regionales;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;
import co.gov.policia.dinae.siedu.modelo.SieduCompetencia;
import co.gov.policia.dinae.siedu.modelo.SieduCompetenciaEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEventoCategoria;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Local
@Remote
public interface APPService {

  /**
   *
   * @return @throws co.gov.policia.dinae.siedu.excepciones.ServiceException
   */
  List<DominioTipo> consultarTiposDominios() throws ServiceException;

  /**
   *
   * @param tipo
   * @return
   * @throws co.gov.policia.dinae.siedu.excepciones.ServiceException
   */
  List<Dominio> consultarDominios(Long tipo) throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<UnidadDependencia> consultarUnidadesVigentes(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @return @throws ServiceException
   */
  List<UnidadDependencia> consultarUnidadesVigentes() throws ServiceException;

  /**
   *
   * @return @throws ServiceException
   */
  List<UnidadDependencia> consultarRegionalesVigentes() throws ServiceException;
  
  /**
   * 
   * @return
   * @throws ServiceException 
   */
   List<Regionales> consultarRegionales() throws ServiceException;

  /**
   *
   * @return @throws ServiceException
   */
  List<UnidadDependencia> consultarEscuelasVigentes() throws ServiceException;

  /**
   *
   * @param regional
   * @return @throws ServiceException
   */
  List<UnidadDependencia> consultarUnidadesFisicasVigentes(Long regional) throws ServiceException;

  /**
   *
   * @return @throws ServiceException
   */
  List<UnidadDependencia> consultarDireccionesYAsesorasVigentes() throws ServiceException;

  /**
   *
   * @return
   */
  List<Categoria> consultarCategorias();

  /**
   *
   * @return
   */
  List<SieduCompetencia> consultarCompetencias();

  /**
   * @param evento
   * @return
   *
   */
  List<SieduEventoCategoria> consultarCategoriasByEvento(SieduEvento evento);

  /**
   * @param evento
   * @return
   */
  List<SieduCompetenciaEvento> consultarCompetenciasByEvento(SieduEvento evento);

  /**
   *
   * @return @throws ServiceException
   */
  List<Grado> consultarGrados() throws ServiceException;

  /**
   *
   * @return @throws ServiceException
   */
  List<NivelesAcademicos> consultarNivelesAcademicos() throws ServiceException;

  /**
   *
   * @param nivelAcademico
   * @return
   * @throws co.gov.policia.dinae.siedu.excepciones.ServiceException
   */
  List<Carreras> consultarCarrerasVigentes(Long nivelAcademico) throws ServiceException;

  /**
   *
   * @param pk
   * @return @throws ServiceException
   */
  List<Grado> consultarGradosVigentesPorCategoria(CategoriaPK pk) throws ServiceException;

  /**
   *
   * @param pk
   * @return @throws ServiceException
   */
  List<Grado> consultarGradosVigentesPorCategorias(List<CategoriaPK> pk) throws ServiceException;

  /**
   *
   * @param idEvento
   * @return @throws ServiceException
   */
  List<Categoria> consultarCategoriasEvento(Long idEvento) throws ServiceException;


  /*
   * @param nivelAcademico
   * @return
   * @throws co.gov.policia.dinae.siedu.excepciones.ServiceException
   */
  List<Carreras> consultarCarrerasVigentesPorCreador(Long nivelAcademico) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  Categoria findById(CategoriaPK id) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  UnidadDependencia findByConsecutivo(UnidadDependenciaPK id) throws ServiceException;  
  
  
  List<Dominio> loadTiposDocumentos()throws ServiceException;;

  /**
   *
   * @return
   */
  List<Dominio> getModalidades();

  /**
   *
   * @return
   */
  List<Dominio> getModalidadesProgramacion();  
  
  /**
   * @return the tiposDominios
   */
  List<DominioTipo> getTiposDominios();

  /**
   * @return the esatdosCiviles
   */
  List<Dominio> getEsatdosCiviles();

  /**
   * @return the estrategias
   */
  List<Dominio> getEstrategias();

  /**
   * @return the tematicas
   */
  List<Dominio> getTematicas();

  /**
   * @return the nivelesAcademicos
   */
  List<NivelesAcademicos> getNivelesAcademicos();

  /**
   * @return the paises
   */
  List<LugarGeografico> getPaises();

  /**
   * @return the paises
   */
  List<LugarGeografico> getMunicipios();

  /**
   * @param pais
   * @return los municipios por pais
   * @throws co.gov.policia.dinae.siedu.excepciones.ServiceException
   */
  List<LugarGeografico> consultarMunicipiosByPais(Long pais) throws ServiceException;

  /**
   * @return the procesos
   */
  List<Dominio> getProcesos();

  /**
   * @return the sexos
   */
  List<Dominio> getSexos();

  /**
   * @return the tiposDocumentos
   */
  List<Dominio> getTiposDocumentos();

  /**
   * @return the tiposIdentificaciones
   */
  List<Dominio> getTiposIdentificaciones();

  /**
   * @return the unidadesDependencias
   */
  List<UnidadDependencia> getUnidadesDependencias();

  /**
   *
   * @param identificacion
   * @return
   */
  PerfilDependenciaDTO getUnidadDependenciaFromUser(String identificacion) throws ServiceException;
  
  /**
   * 
   * @param id
   * @return
   * @throws ServiceException 
   */
  Dominio getDescrDominio(Long id) throws ServiceException;
}
