package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.PeriodoDTO;
import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IPeriodoLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IkeyPropertiesLocal;
import co.gov.policia.dinae.interfaces.UtilJPA;
import co.gov.policia.dinae.modelo.ConcecutivoLiberaConvocatoria;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.TipoUnidad;
import co.gov.policia.dinae.modelo.TipoUnidadPeriodo;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.UnidadPolicialPeriodo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Stateless
public class PeriodoEjbBean implements IPeriodoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  @EJB
  private IUnidadPolicialLocal iUnidadPolicialLocal;

  @EJB
  private IkeyPropertiesLocal ikeyPropertiesLocal;

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<Periodo> getPeriodosNecesidades() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("Periodo.findAllNecesidades")
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   * Se conculta la lista de los periodos en los cuales la fecha actual se encuentra entre la fecha de inicio y fecha fin del periodo (periodo de apertura) y está habilitada para la unidad del usuario
   * para este cronograma.
   *
   * @param fechaActual
   * @param idUnidadPolicial
   * @return lista de periodos
   * @throws JpaDinaeException
   */
  @Override
  public List<Periodo> getPeriodosFechaActualEntreFechaInicioYfechaFin(Date fechaActual, Long idUnidadPolicial, Character estadoPeriodo) throws JpaDinaeException {

    try {

      //CONSULTAMOS LOS PERIODOS
      List<Periodo> listaPeriodo = entityManager.createNamedQuery("UnidadPolicialPeriodo.findAllPeriodoByFechaActualEntreFechaInicioYfechaFinYestado")
              .setParameter("fechaActual", fechaActual, TemporalType.DATE)
              .setParameter("idUnidadPolicial", idUnidadPolicial)
              .setParameter("estadoPeriodo", estadoPeriodo)
              .getResultList();

      return listaPeriodo;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param fechaActual
   * @param idUnidadPolicial
   * @param idEstadoConvocatoria
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<Periodo> getPeriodosFechaActualEntreFechaInicioYfechaFinUnidadPolicialYestadoConvocatoria(Date fechaActual, Long idUnidadPolicial, Long idEstadoConvocatoria) throws JpaDinaeException {

    try {

      //CONSULTAMOS LOS PERIODOS
      List<Periodo> listaPeriodo = entityManager.createNamedQuery("UnidadPolicialPeriodo.findAllPeriodosFechaActualEntreFechaInicioYfechaFinUnidadPolicialYestadoConvocatoria")
              .setParameter("fechaActual", fechaActual, TemporalType.DATE)
              .setParameter("idUnidadPolicial", idUnidadPolicial)
              .setParameter("idEstadoConvocatoria", idEstadoConvocatoria)
              .getResultList();

      return listaPeriodo;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param fechaActual
   * @param idUnidadPolicial
   * @param idEstadoConvocatoria
   * @param idTipoConvocatoria
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<Periodo> getPeriodosFechaActualEntreFechaInicioYfechaFinUnidadPolicialYestadoConvocatoriaYtipoConvocatoria(Date fechaActual, Long idUnidadPolicial, Long idEstadoConvocatoria, Long idTipoConvocatoria) throws JpaDinaeException {

    try {

      //CONSULTAMOS LOS PERIODOS
      List<Periodo> listaPeriodo = entityManager.createNamedQuery("UnidadPolicialPeriodo.findAllPeriodosFechaActualEntreFechaInicioYfechaFinUnidadPolicialYestadoConvocatoriaYtipoConvocatoria")
              .setParameter("fechaActual", fechaActual, TemporalType.DATE)
              .setParameter("idUnidadPolicial", idUnidadPolicial)
              .setParameter("idEstadoConvocatoria", idEstadoConvocatoria)
              .setParameter("idTipoPeriodo", idTipoConvocatoria)
              .getResultList();

      return listaPeriodo;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param tipoUnidad
   * @param periodo
   * @param listaUnidadPolicialDTO
   * @throws JpaDinaeException
   */
  private void agregarTipoUnidadesPeriodo(List<TipoUnidad> tipoUnidad, Periodo periodo, List<UnidadPolicialDTO> listaUnidadPolicialDTO) throws JpaDinaeException {

    List<TipoUnidadPeriodo> listTipoUnidadPeriodo = new ArrayList<TipoUnidadPeriodo>();
    periodo.setTipoUnidadPeriodoList(listTipoUnidadPeriodo);

    for (TipoUnidad tp : tipoUnidad) {
      TipoUnidadPeriodo tuP = new TipoUnidadPeriodo();
      tuP.setTipoUnidad(tp);
      tuP.setPeriodo(periodo);
      entityManager.persist(tuP);
    }
    List<UnidadPolicialPeriodo> unidadPolicialPeriodos = new ArrayList<UnidadPolicialPeriodo>();
    periodo.setUnidadPolicialPeriodoList(unidadPolicialPeriodos);

    for (UnidadPolicialDTO uP : listaUnidadPolicialDTO) {

      if (uP.isSeleccionado()) {

        UnidadPolicialPeriodo uPp = new UnidadPolicialPeriodo();
        uPp.setPeriodo(periodo);
        uPp.setUnidadPolicial(new UnidadPolicial(uP.getIdUnidadPolicial()));

        entityManager.persist(uPp);
      }
    }
  }

  /**
   * Método que realiza el proceso de insertar un periodo, insertando las unidades a las cuales aplica ese periodo dependiendo del tipo de unidad seleccionado.
   *
   * @param tipoUnidad
   * @param descripcion
   * @param fechaInicio
   * @param fechafina
   * @param cantidad
   * @param estado
   * @throws JpaDinaeException
   */
  @Override
  public Periodo guardarPeriodo(Integer anio, List<TipoUnidad> tipoUnidad, String descripcion, Date fechaInicio, Date fechafina, Integer cantidad, Character estado, List<UnidadPolicialDTO> listaUnidadPolicialDTO) throws JpaDinaeException {
    try {
      //Se arma objecto periodo
      Periodo periodo = new Periodo();
      periodo.setAnio(anio.shortValue());
      periodo.setCantidad(cantidad.shortValue());
      periodo.setDescripcion(descripcion);
      periodo.setFechaInicio(fechaInicio);
      periodo.setFechaFin(fechafina);
      periodo.setEstado(estado);
      periodo.setTipoRegistro("NECESIDAD");
      //Se arma objeto TipoUnidadPeridio
      entityManager.persist(periodo);

      agregarTipoUnidadesPeriodo(tipoUnidad, periodo, listaUnidadPolicialDTO);

      return periodo;
    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
    //entityManager.refresh(periodo);
  }

  /**
   *
   * @param fechaActual
   * @param estado
   * @return
   * @throws co.gov.policia.dinae.exceptions.JpaDinaeException
   */
  @Override
  public List<Periodo> getPeriodosPorEstadoYFechaActualEntreFechaInicioYfechaFin(Date fechaActual, Character estado) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("Periodo.findByEstadoYFechaInicioFechaFinEntre")
              .setParameter("estado", estado)
              .setParameter("fechaActual", fechaActual, TemporalType.DATE)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }

  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<Periodo> getPeriodosPorEstado(Character estado) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("Periodo.findByEstado")
              .setParameter("estado", estado)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<Long> getIdsUnidadesPolicialesPorPeriodo(Long idPeriodo) throws JpaDinaeException {

    try {

      return entityManager.createQuery("SELECT p.unidadPolicial.idUnidadPolicial FROM UnidadPolicialPeriodo p WHERE p.periodo.idPeriodo = :idPeriodo")
              .setParameter("idPeriodo", idPeriodo)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);

    }
  }

  /**
   *
   */
  @Override
  public void modificarPeriodio(Periodo periodo, List<UnidadPolicialDTO> listaUnidadPolicialDTO) throws JpaDinaeException {
    try {

      periodo.setTipoRegistro("NECESIDAD");
      entityManager.merge(periodo);
      /**
       * Se elimian las unidades policiales asociadas al periodo
       */
      entityManager.createQuery("DELETE FROM UnidadPolicialPeriodo WHERE  periodo.idPeriodo = :periodo")
              .setParameter("periodo", periodo.getIdPeriodo())
              .executeUpdate();
      /**
       * Se eliminan los tipos de unidadaes asocidas al periodo
       */
      entityManager.createQuery("DELETE FROM TipoUnidadPeriodo WHERE  periodo.idPeriodo = :periodo")
              .setParameter("periodo", periodo.getIdPeriodo())
              .executeUpdate();

      List<TipoUnidad> tipoUnidades = new ArrayList<TipoUnidad>();

      for (TipoUnidadPeriodo tUP : periodo.getTipoUnidadPeriodoList()) {
        tipoUnidades.add(tUP.getTipoUnidad());
      }

      agregarTipoUnidadesPeriodo(tipoUnidades, periodo, listaUnidadPolicialDTO);

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param convocatoria
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public Periodo guardarConvocatoria(Periodo convocatoria) throws JpaDinaeException {

    try {

      if (convocatoria.getIdPeriodo() != null) {

        /**
         * Se elimian las unidades policiales asociadas al periodo
         */
        entityManager.createQuery("DELETE FROM UnidadPolicialPeriodo WHERE  periodo.idPeriodo = :periodo")
                .setParameter("periodo", convocatoria.getIdPeriodo())
                .executeUpdate();
        /**
         * Se eliminan los tipos de unidadaes asocidas al periodo
         */
        entityManager.createQuery("DELETE FROM TipoUnidadPeriodo WHERE  periodo.idPeriodo = :periodo")
                .setParameter("periodo", convocatoria.getIdPeriodo())
                .executeUpdate();

      }

      convocatoria.setTipoRegistro("CONVOCATORIA");

      if (convocatoria.getIdPeriodo() == null) {

        Long numeroConcecutivo;
        if (convocatoria.getTipoPeriodo().getIdConstantes().equals(IConstantes.TIPO_CONVOCATORIA_GESTION_PARA_FINANCIACION)) {

          numeroConcecutivo = ikeyPropertiesLocal.getConcecutivoConvocatoriaPropertiePorClave(
                  IConstantes.CLAVE_KP_CU_CU_CO_1_LBL_NUMERO_CONVOCATORIA_FINANCIA_NUM);
        } else {

          numeroConcecutivo = ikeyPropertiesLocal.getConcecutivoConvocatoriaPropertiePorClave(
                  IConstantes.CLAVE_KP_CU_CU_CO_1_LBL_NUMERO_CONVOCATORIA_ENSAYO_NUM);

        }

        convocatoria.setConcecutivo(numeroConcecutivo);
        entityManager.persist(convocatoria);

        return convocatoria;

      }

      //SI ESTAMOS MODIFIACANDO
      //CONSULTAMOS LA CONVOCATORIA QUE EXISTE ACTUALMENTE
      Periodo convocatoriaBDConsulta = entityManager.find(Periodo.class, convocatoria.getIdPeriodo());

      Long idTipoPeriodoBDconsulta = convocatoriaBDConsulta.getTipoPeriodo().getIdConstantes();
      Long idEstadoConvocatoria = convocatoriaBDConsulta.getEstadoConvocatorio().getIdConstantes();
      Long numeroConcecutivoLibera = convocatoriaBDConsulta.getConcecutivo();

      //VALIDAMOS SI EL ACTOR REQUIERE MODIFICAR EL TIPO DE CONVOCATORIA
      if (!idTipoPeriodoBDconsulta.equals(convocatoria.getTipoPeriodo().getIdConstantes())) {
        //EL USUARIO A CAMBIADO EL TIPO DE CONVOCATORIA
        //SEVALIDA QUE LA CONVOCATORIA SE ENCUENTRE EN ESTADO NUEVA
        if (idEstadoConvocatoria.equals(IConstantes.TIPO_ESTADO_CONVOCATORIA_NUEVA)) {

          //EL SISTEMA DEBE MODIFICAR EL NÚMERO DE LA CONVOCATORIA,
          Long numeroConcecutivo;
          if (convocatoria.getTipoPeriodo().getIdConstantes().equals(IConstantes.TIPO_CONVOCATORIA_GESTION_PARA_FINANCIACION)) {
            numeroConcecutivo = ikeyPropertiesLocal.getConcecutivoConvocatoriaPropertiePorClave(
                    IConstantes.CLAVE_KP_CU_CU_CO_1_LBL_NUMERO_CONVOCATORIA_FINANCIA_NUM);
          } else {
            numeroConcecutivo = ikeyPropertiesLocal.getConcecutivoConvocatoriaPropertiePorClave(
                    IConstantes.CLAVE_KP_CU_CU_CO_1_LBL_NUMERO_CONVOCATORIA_ENSAYO_NUM);

          }
          convocatoria.setConcecutivo(numeroConcecutivo);

          entityManager.merge(convocatoria);
          //MANTENIENDO EL CONSECUTIVO DE ACUERDO AL TIPO DE CONVOCATORIA Y
          //LIBERANDO EL NÚMERO QUE NO SERÁ ASIGNADO.

          ConcecutivoLiberaConvocatoria concecutivoLiberaConvocatoria = new ConcecutivoLiberaConvocatoria();
          concecutivoLiberaConvocatoria.setConcecutivoLiberado(numeroConcecutivoLibera);

          if (idTipoPeriodoBDconsulta.equals(IConstantes.TIPO_CONVOCATORIA_GESTION_PARA_FINANCIACION)) {
            concecutivoLiberaConvocatoria.setClaveFinanciaEnsayo(IConstantes.CLAVE_KP_CU_CU_CO_1_LBL_NUMERO_CONVOCATORIA_FINANCIA_NUM);
          } else {
            concecutivoLiberaConvocatoria.setClaveFinanciaEnsayo(IConstantes.CLAVE_KP_CU_CU_CO_1_LBL_NUMERO_CONVOCATORIA_ENSAYO_NUM);
          }
          //GUARDAMOS EL CONCECUTIVO PARA UNO NUEV USO
          entityManager.persist(concecutivoLiberaConvocatoria);

        }
      }

      entityManager.merge(convocatoria);

      return convocatoria;

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }
  }

  /**
   *
   * @param idTipoConvocatoria
   * @param listaIdEstado Filtro de estados, Si la lista es vacia o nulo no se aplica el filtro de estados
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<PeriodoDTO> getListaConvocatoriasPorTipoConvocatoria(Long idTipoConvocatoria, List<Long> listaIdEstado) throws JpaDinaeException {

    try {

      if (listaIdEstado == null || listaIdEstado.isEmpty()) {

        return entityManager.createNamedQuery("PeriodoDTO.findAllPorTipoConvocatoria")
                .setParameter("idConstantes", idTipoConvocatoria)
                .setParameter("tipoRegistro", "CONVOCATORIA")
                .getResultList();
      } else {

        String sql = "SELECT NEW co.gov.policia.dinae.dto.PeriodoDTO( c.idPeriodo, c.nombreConvocatorio, c.concecutivo, c.estadoConvocatorio.valor, c.estadoConvocatorio.idConstantes, c.fechaInicio, c.fechaFin ) FROM Periodo c "
                + " WHERE c.tipoPeriodo.idConstantes = :idConstantes "
                + " AND c.tipoRegistro = :tipoRegistro "
                + " AND c.estadoConvocatorio.idConstantes IN " + UtilJPA.generateCollection(listaIdEstado, false) + " ORDER BY c.concecutivo";

        return entityManager.createQuery(sql)
                .setParameter("idConstantes", idTipoConvocatoria)
                .setParameter("tipoRegistro", "CONVOCATORIA")
                .getResultList();
      }

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }

  }

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public Periodo getPeriodoPorId(Long idPeriodo) throws JpaDinaeException {

    try {

      return entityManager.find(Periodo.class, idPeriodo);

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }

  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<Periodo> findConvocaEnsayoCriticoAbiertaPublicada() throws JpaDinaeException {

    try {

      List<Periodo> results = entityManager.createNamedQuery("Periodo.findConvocaEnsayoCriticoAbiertaPublicada", Periodo.class)
              .setParameter("estadoConvocatoria", IConstantes.TIPO_ESTADO_CONVOCATORIA_PUBLICADA)
              .setParameter("fechaActual", new Date(), TemporalType.DATE)
              .setParameter("tipoPeriodo", IConstantes.TIPO_CONVOCATORIA_GESTION_ENSAYO_CRITICO)
              .getResultList();

      return results;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<Periodo> getListaConvocatoriasParaBusquedaProyectos() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("Periodo.findAllConvocatoriasParaBusquedaProyectos")
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<Periodo> getTodosAniosHabilitadosParaProyectosInstitucionales() throws JpaDinaeException {

    try {
      return entityManager.createNamedQuery("Periodo.DISTINCTfindAllAniosProyectosInstitucionales")
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }

  }

  /**
   *
   * @param criterios
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<PeriodoDTO> getListaConvocatoriasPorTipoConvocatoriaFiltros(Map<String, Object> criterios) throws JpaDinaeException {

    try {

      StringBuilder jpql = new StringBuilder("SELECT NEW co.gov.policia.dinae.dto.PeriodoDTO"
              + "(c.idPeriodo, c.nombreConvocatorio, c.concecutivo, c.estadoConvocatorio.valor, c.estadoConvocatorio.idConstantes, c.fechaInicio, c.fechaFin) "
              + " FROM Periodo c WHERE c.tipoRegistro = 'CONVOCATORIA' ");

      Map<String, Object> params = new HashMap<String, Object>();

      if (!criterios.keySet().isEmpty()) {

        if (criterios.containsKey(IConstantes.CRITERIO_CONVOCATORIAS_TIPO_CONVOCATORIA)) {
          String sqlPart = " AND c.tipoPeriodo.idConstantes = :idTipoPeriodo";
          jpql.append(sqlPart);
          params.put("idTipoPeriodo", criterios.get(IConstantes.CRITERIO_CONVOCATORIAS_TIPO_CONVOCATORIA));
        }

        if (criterios.containsKey(IConstantes.CRITERIO_CONVOCATORIAS_ESTADO_CONVOCATORIA)) {
          String sqlPart = " AND c.estadoConvocatorio.idConstantes = :idEstadoConvocatoria";
          jpql.append(sqlPart);
          params.put("idEstadoConvocatoria", criterios.get(IConstantes.CRITERIO_CONVOCATORIAS_ESTADO_CONVOCATORIA));
        }

        if (criterios.containsKey(IConstantes.CRITERIO_CONVOCATORIAS_CONSECUTIVO)) {
          String sqlPart = " AND (c.concecutivoConvocatoria = :consecutivo OR c.concecutivo = :consecutivo)";
          jpql.append(sqlPart);
          params.put("consecutivo", criterios.get(IConstantes.CRITERIO_CONVOCATORIAS_CONSECUTIVO));
        }

        if (criterios.containsKey(IConstantes.CRITERIO_CONVOCATORIAS_PALABRA_CLAVE)) {
          String sqlPart = " AND (UPPER(c.nombreConvocatorio) LIKE UPPER(:palabraClave) OR UPPER(c.descripcion) LIKE UPPER(:palabraClave))";
          jpql.append(sqlPart);
          params.put("palabraClave", "%" + criterios.get(IConstantes.CRITERIO_CONVOCATORIAS_PALABRA_CLAVE) + "%");
        }

        if (criterios.containsKey(IConstantes.CRITERIO_CONVOCATORIAS_FECHA_INICIAL)
                && criterios.containsKey(IConstantes.CRITERIO_CONVOCATORIAS_FECHA_FINAL)) {
          String sqlPart = " AND ( c.fechaInicio BETWEEN :fechaInicio AND :fechaFin OR c.fechaFin BETWEEN :fechaInicio AND :fechaFin ) ";
          jpql.append(sqlPart);
          params.put("fechaInicio", criterios.get(IConstantes.CRITERIO_CONVOCATORIAS_FECHA_INICIAL));
          params.put("fechaFin", criterios.get(IConstantes.CRITERIO_CONVOCATORIAS_FECHA_FINAL));
        }
      }

      jpql.append(" ORDER BY c.concecutivo ASC ");

      Query query = entityManager.createQuery(jpql.toString());
      Set<String> keys = params.keySet();

      for (String key : keys) {
        query.setParameter(key, params.get(key));
      }

      return query.getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idUnidadPolicial
   * @param fechaActual
   * @param estadoPeriodo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<Periodo> findAllConvocatoriasByFechaActualEntreFechaInicioYfechaFinYestado(Long idUnidadPolicial, Date fechaActual, Character estadoPeriodo) throws JpaDinaeException {
    try {

      //CONSULTAMOS LOS PERIODOS
      List<Periodo> listaPeriodo = entityManager.createNamedQuery("UnidadPolicialPeriodo.findAllConvocatoriasByFechaActualEntreFechaInicioYfechaFinYestado")
              .setParameter("fechaActual", fechaActual, TemporalType.DATE)
              .setParameter("idUnidadPolicial", idUnidadPolicial)
              .setParameter("estadoPeriodo", estadoPeriodo)
              .getResultList();

      return listaPeriodo;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param idPeriodo
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public int getNumeroPropuestasNecesidadesDashBoard(Long idPeriodo, Long idUnidadPolicial) throws JpaDinaeException {

    try {

      Object cuantos = entityManager.createNamedQuery("PropuestaNecesidad.cuentaPropuestasDashBoard")
              .setParameter("idPeriodo", idPeriodo)
              .setParameter("idUnidadPolicial", idUnidadPolicial)
              .getSingleResult();

      if (cuantos == null) {
        return 0;
      }

      return ((Number) cuantos).intValue();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }

  }

  /**
   *
   * @param idUnidadPolicial
   * @param idEstadoConvocatoria
   * @param idTipoConvocatoria
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<Periodo> getPeriodosUnidadPolicialYestadoConvocatoriaYtipoConvocatoria(Long idUnidadPolicial, Long idEstadoConvocatoria, Long idTipoConvocatoria) throws JpaDinaeException {

    try {

      //CONSULTAMOS LOS PERIODOS
      List<Periodo> listaPeriodo = entityManager.createNamedQuery("UnidadPolicialPeriodo.findAllPeriodosUnidadPolicialYestadoConvocatoriaYtipoConvocatoria")
              .setParameter("idUnidadPolicial", idUnidadPolicial)
              .setParameter("idEstadoConvocatoria", idEstadoConvocatoria)
              .setParameter("idTipoPeriodo", idTipoConvocatoria)
              .getResultList();

      return listaPeriodo;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }

  }

  /**
   *
   * @param idConvocatoria
   * @param nombreFisicoArchivo
   * @param nombreOriginalArchivo
   * @throws JpaDinaeException
   */
  @Override
  public void actualizarConvocatoriaReporteEnsayoCritico(Long idConvocatoria, String nombreFisicoArchivo, String nombreOriginalArchivo) throws JpaDinaeException {

    try {

      entityManager.createNamedQuery("Periodo.UpdateArchivosConvocatoriaEnsayo")
              .setParameter("nombreArchivoFisicoConvocatoriaEnsayo", nombreFisicoArchivo)
              .setParameter("nombreArchivoOriginalConvocatoriaEnsayo", nombreOriginalArchivo)
              .setParameter("idPeriodo", idConvocatoria)
              .setParameter("estado", new Constantes(IConstantes.TIPO_ESTADO_CONVOCATORIA_CULMINADA))
              .executeUpdate();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);

    }
  }

  /**
   *
   * @param idConvocatoria
   * @param nombreFisicoArchivo
   * @param nombreOriginalArchivo
   * @throws JpaDinaeException
   */
  @Override
  public void actualizarConvocatoriaReportePropuestaConvocatoria(Long idConvocatoria, String nombreFisicoArchivo, String nombreOriginalArchivo) throws JpaDinaeException {

    try {

      entityManager.createNamedQuery("Periodo.UpdateArchivosPropuestaConvocatoriaReporte")
              .setParameter("nombreArchivoFisicoPropuestaConvocatoriaReporte", nombreFisicoArchivo)
              .setParameter("nombreArchivoOriginalPropuestaConvocatoriaReporte", nombreOriginalArchivo)
              .setParameter("idPeriodo", idConvocatoria)
              .executeUpdate();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);

    }
  }
}
