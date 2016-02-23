package com.tacticlogistics.crm.view.backing;

import com.tacticlogistics.crm.model.entities.Clientes;
import com.tacticlogistics.crm.model.entities.Destinatarios;
import com.tacticlogistics.crm.model.entities.EstadosPedidos;
import com.tacticlogistics.crm.model.entities.Pedidos;
import com.tacticlogistics.crm.model.entities.PedidosDestinos;
import com.tacticlogistics.crm.model.entities.PedidosProductos;
import com.tacticlogistics.crm.model.entities.PrioridadesPedidos;
import com.tacticlogistics.crm.model.entities.PuntosEntrega;
import com.tacticlogistics.crm.model.entities.TiposFormaPago;
import com.tacticlogistics.crm.model.entities.TiposPedido;
import com.tacticlogistics.crm.model.hibernate.criteria.HibernateRestriction;
import com.tacticlogistics.crm.view.util.DataModelCustom;
import com.tacticlogistics.crm.view.util.JSFUtil;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

@Named
@Scope("session")
public class PedidosBacking extends BaseBacking {

    private String mode;

    private Pedidos selected;
    private List<Pedidos> list;
    private DataModelCustom<Pedidos> model;

    private Integer idDestinatario;
    private Integer idEstadosPedidos;
    private Date fechaPedidoInicial;
    private Date fechaPedidoFinal;
    private Integer idPuntoEntrega;
    private String numeroDocumentoSolicitud;
    private Integer cantidadSolicitada;
    private String emailsContacto;
    private String telefonosContacto;
    private String loteSugerido;

    private Destinatarios destinatarios;

    private DataModelCustom<PuntosEntrega> modelPuntosEntrega;
    private PuntosEntrega selectedPuntosEntrega;
    private List<PuntosEntrega> listSelectedPuntosEntrega;
    private List<SelectItem> listItemPuntosEntrega;

    private List<PedidosProductos> listPedidosProductos;
    private List<PedidosProductos> listPedidosProductosPE;
    private DataModelCustom<PedidosProductos> modelPedidosProductos;
    private List<PedidosProductos> listSelectedPedidosProductos;
    private List<PedidosDestinos> listPedidosDestinos;

    private List<SelectItem> listEstadosDisponibles;
    private Integer idEstadosDisponibles;
    private Map<Integer, EstadosPedidos> mapEstadosDisponibles;

    public PedidosBacking() {
        newAction();
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Pedidos getSelected() {
        return selected;
    }

    public void setSelected(Pedidos selected) {
        this.selected = selected;
    }

    public List<Pedidos> getList() {
        return list;
    }

    public void setList(List<Pedidos> list) {
        this.list = list;
    }

    public DataModelCustom<Pedidos> getModel() {
        return model;
    }

    public void setModel(DataModelCustom<Pedidos> model) {
        this.model = model;
    }

    public Integer getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(Integer idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public Integer getIdEstadosPedidos() {
        return idEstadosPedidos;
    }

    public void setIdEstadosPedidos(Integer idEstadosPedidos) {
        this.idEstadosPedidos = idEstadosPedidos;
    }

    public Date getFechaPedidoInicial() {
        return fechaPedidoInicial;
    }

    public void setFechaPedidoInicial(Date fechaPedidoInicial) {
        this.fechaPedidoInicial = fechaPedidoInicial;
    }

    public Date getFechaPedidoFinal() {
        return fechaPedidoFinal;
    }

    public void setFechaPedidoFinal(Date fechaPedidoFinal) {
        this.fechaPedidoFinal = fechaPedidoFinal;
    }

    public Integer getIdPuntoEntrega() {
        return idPuntoEntrega;
    }

    public void setIdPuntoEntrega(Integer idPuntoEntrega) {
        this.idPuntoEntrega = idPuntoEntrega;
    }

    public String getNumeroDocumentoSolicitud() {
        return numeroDocumentoSolicitud;
    }

    public void setNumeroDocumentoSolicitud(String numeroDocumentoSolicitud) {
        this.numeroDocumentoSolicitud = numeroDocumentoSolicitud;
    }

    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Integer cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public DataModelCustom<PuntosEntrega> getModelPuntosEntrega() {
        return modelPuntosEntrega;
    }

    public void setModelPuntosEntrega(DataModelCustom<PuntosEntrega> modelPuntosEntrega) {
        this.modelPuntosEntrega = modelPuntosEntrega;
    }

    public PuntosEntrega getSelectedPuntosEntrega() {
        return selectedPuntosEntrega;
    }

    public void setSelectedPuntosEntrega(PuntosEntrega selectedPuntosEntrega) {
        this.selectedPuntosEntrega = selectedPuntosEntrega;
    }

    public List<PuntosEntrega> getListSelectedPuntosEntrega() {
        return listSelectedPuntosEntrega;
    }

    public void setListSelectedPuntosEntrega(List<PuntosEntrega> listSelectedPuntosEntrega) {
        this.listSelectedPuntosEntrega = listSelectedPuntosEntrega;
    }

    public List<SelectItem> getListItemPuntosEntrega() {
        return listItemPuntosEntrega;
    }

    public void setListItemPuntosEntrega(List<SelectItem> listItemPuntosEntrega) {
        this.listItemPuntosEntrega = listItemPuntosEntrega;
    }

    public List<PedidosProductos> getListPedidosProductos() {
        return listPedidosProductos;
    }

    public void setListPedidosProductos(List<PedidosProductos> listPedidosProductos) {
        this.listPedidosProductos = listPedidosProductos;
    }

    public List<PedidosProductos> getListPedidosProductosPE() {
        return listPedidosProductosPE;
    }

    public void setListPedidosProductosPE(List<PedidosProductos> listPedidosProductosPE) {
        this.listPedidosProductosPE = listPedidosProductosPE;
    }

    public DataModelCustom<PedidosProductos> getModelPedidosProductos() {
        return modelPedidosProductos;
    }

    public void setModelPedidosProductos(DataModelCustom<PedidosProductos> modelPedidosProductos) {
        this.modelPedidosProductos = modelPedidosProductos;
    }

    public List<PedidosProductos> getListSelectedPedidosProductos() {
        return listSelectedPedidosProductos;
    }

    public void setListSelectedPedidosProductos(List<PedidosProductos> listSelectedPedidosProductos) {
        this.listSelectedPedidosProductos = listSelectedPedidosProductos;
    }

    public List<PedidosDestinos> getListPedidosDestinos() {
        return listPedidosDestinos;
    }

    public void setListPedidosDestinos(List<PedidosDestinos> listPedidosDestinos) {
        this.listPedidosDestinos = listPedidosDestinos;
    }

    public Map<Integer, EstadosPedidos> getMapEstadosDisponibles() {
        return mapEstadosDisponibles;
    }

    public void setMapEstadosDisponibles(Map<Integer, EstadosPedidos> mapEstadosDisponibles) {
        this.mapEstadosDisponibles = mapEstadosDisponibles;
    }

    public List<SelectItem> getListEstadosDisponibles() {
        return listEstadosDisponibles;
    }

    public void setListEstadosDisponibles(List<SelectItem> listEstadosDisponibles) {
        this.listEstadosDisponibles = listEstadosDisponibles;
    }

    public Integer getIdEstadosDisponibles() {
        return idEstadosDisponibles;
    }

    public void setIdEstadosDisponibles(Integer idEstadosDisponibles) {
        this.idEstadosDisponibles = idEstadosDisponibles;
    }

    public String getEmailsContacto() {
        return emailsContacto;
    }

    public void setEmailsContacto(String emailsContacto) {
        this.emailsContacto = emailsContacto;
    }

    public String getTelefonosContacto() {
        return telefonosContacto;
    }

    public void setTelefonosContacto(String telefonosContacto) {
        this.telefonosContacto = telefonosContacto;
    }

    public String getLoteSugerido() {
        return loteSugerido;
    }

    public void setLoteSugerido(String loteSugerido) {
        this.loteSugerido = loteSugerido;
    }

    public String newAction() {
        mode = "EDIT";
        fechaPedidoInicial = null;
        fechaPedidoFinal = null;
        idClientes = null;
        selected = new Pedidos();
        selected.setDestinatarios(new Destinatarios());
        selected.setClientes(new Clientes());
        selected.setTiposFormaPago(new TiposFormaPago(1));
        selected.setPrioridadesPedidos(new PrioridadesPedidos(1));
        selected.setTiposPedido(new TiposPedido());
        selected.setFechaSolicitudPedido(new Date());
        selectedPuntosEntrega = new PuntosEntrega();
        listSelectedPuntosEntrega = null;
        listPedidosProductos = new LinkedList<>();
        modelPedidosProductos = new DataModelCustom<>(listPedidosProductos);
        return "/pedidos/nuevo?faces-redirect=true";
    }

    public String returnAction() {
        return "/pedidos/gestion?faces-redirect=true";
    }

    public void searchAction(ActionEvent event) {
        try {
            Clientes clientes = new Clientes();
            clientes.setId((idClientes == null || idClientes == 0) ? null : idClientes);

            EstadosPedidos estadosPedidos = new EstadosPedidos();
            estadosPedidos.setId((idEstadosPedidos == null || idEstadosPedidos == 0) ? null : idEstadosPedidos);

            Pedidos pedidos = new Pedidos();
            pedidos.setClientes(clientes);
            pedidos.setNumeroDocumento(numeroDocumentoSolicitud);
            pedidos.setEstadosPedidos(estadosPedidos);

            List<HibernateRestriction> conditionCriteriaList = new LinkedList<>();
            list = pedidosBO.getListPedidos(pedidos, conditionCriteriaList);
            model = new DataModelCustom<>(list);
            selected = null;
        } catch (Exception ex) {
            Logger.getLogger(PedidosBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String updateAction() {
        try {
            idPuntoEntrega = null;
            parametroProducto = null;
            listProductosTemp = null;
            modelProductosTemp = null;
            selectedProductosTemp = null;
            listPedidosProductosPE = new LinkedList<>();
            modelPedidosProductos = new DataModelCustom<>(listPedidosProductosPE);

            idClientes = selected.getClientes().getId();
            //listPuntosEntrega = clientesBO.getListPuntosEntregaPorDestinatario(selected.getDestinatarios().getId());
            //modelPuntosEntrega = new DataModelCustom<>(listPuntosEntrega);
            listSelectedPuntosEntrega = new LinkedList<>();

            listPedidosDestinos = pedidosBO.getListPedidosDestinos(selected);
            listPedidosDestinos.stream().forEach((row) -> {
                listSelectedPuntosEntrega.add(row.getPuntosEntrega());
            });

            listPedidosProductos = pedidosBO.getListPedidosProductos(selected);

            puntosEntregaSelectedAction(null);
        } catch (Exception ex) {
            Logger.getLogger(PedidosBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/pedidos/nuevo?faces-redirect=true";
    }

    public void updateEstadoAction(ActionEvent event) {
        try {
            idEstadosDisponibles = null;
            listEstadosDisponibles = new LinkedList<>();
            mapEstadosDisponibles = new HashMap<>();

            EstadosPedidos mayor = catalogoBO.getEstadosPedidosMayor(selected.getEstadosPedidos().getOrdinal());
            EstadosPedidos menor = catalogoBO.getEstadosPedidosMenor(selected.getEstadosPedidos().getOrdinal());

            if (mayor != null && !"99".equals(mayor.getCodigo())) {
                mapEstadosDisponibles.put(mayor.getId(), mayor);
                listEstadosDisponibles.add(new SelectItem(mayor.getId(), mayor.getNombre()));
            }

            if (menor != null && !"99".equals(menor.getCodigo())) {
                mapEstadosDisponibles.put(menor.getId(), menor);
                listEstadosDisponibles.add(new SelectItem(menor.getId(), menor.getNombre()));
            }

            if (!"99".equals(selected.getEstadosPedidos().getCodigo())) {
                EstadosPedidos filter = new EstadosPedidos();
                filter.setCodigo("99");
                EstadosPedidos anulado = catalogoBO.getEstadosPedidos(filter);
                mapEstadosDisponibles.put(anulado.getId(), anulado);
                listEstadosDisponibles.add(new SelectItem(anulado.getId(), anulado.getNombre()));
            }
        } catch (Exception ex) {
            Logger.getLogger(PedidosBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveEstadoAction(ActionEvent event) {
        try {
            selected.setEstadosPedidos(mapEstadosDisponibles.get(idEstadosDisponibles));
            catalogoBO.save(selected);
            String message = JSFUtil.getMessage("msg_ok");
            JSFUtil.addInfo(message);
        } catch (Exception ex) {
            Logger.getLogger(PedidosBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveAction(ActionEvent event) {
        try {
            boolean validate = true;
            if (listSelectedPuntosEntrega == null || listSelectedPuntosEntrega.isEmpty()) {
                String message = JSFUtil.getMessage("oc_mensaje_2");
                JSFUtil.addWarn(message);
                validate = false;
            }

            if (listPedidosProductos == null || listPedidosProductos.isEmpty()) {
                String message = JSFUtil.getMessage("oc_mensaje_5");
                JSFUtil.addWarn(message);
                validate = false;
            }

            if (validate) {
                Short numLinea = 1;
                for (PedidosDestinos pedidosDestinos : listPedidosDestinos) {
                    pedidosDestinos.setNumeroLinea(numLinea);
                    Short numLineaProducto = 1;
                    pedidosDestinos.setPedidosProductoses(new LinkedList<>());
                    for (PedidosProductos row2 : listPedidosProductos) {
                        if (Objects.equals(pedidosDestinos.getPuntosEntrega(), row2.getPedidosDestinos().getPuntosEntrega().getId())) {
                            row2.setNumeroLinea(numLineaProducto);
                            row2.setPedidosDestinos(pedidosDestinos);
                            row2.setUsuarioActualizacion("csarmiento");
                            row2.setFechaActualizacion(new Date());
                            row2.setCantidadDespachada(0);
                            row2.setValorDeclaradoPorUoMDespachada(BigDecimal.ZERO);
                            pedidosDestinos.getPedidosProductoses().add(row2);
                            numLineaProducto++;
                        }
                    }
                    numLinea++;
                }

                Clientes clientes = new Clientes();
                clientes.setId(idClientes);

                EstadosPedidos estadosPedidos = new EstadosPedidos();
                estadosPedidos.setId(1);
                selected.setClientes(clientes);
                selected.setEstadosPedidos(estadosPedidos);
                selected.setPedidosDestinoses(listPedidosDestinos);
                selected.setFechaActualizacion(new Date());
                selected.setUsuarioActualizacion("csarmiento");
                selected.setActivo(Boolean.TRUE);
                selected.setFechaSolicitudPedido(new Date());
                selected.setObservacionInterna("");

                catalogoBO.save(selected);
                String message = JSFUtil.getMessage("msg_ok");
                JSFUtil.addInfo(message);

                searchAction(null);
            }
        } catch (Exception ex) {
            JSFUtil.addError(ex.getMessage());
            Logger.getLogger(PedidosBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void puntosEntregaSelectedAction(ActionEvent event) {
        listItemPuntosEntrega = new LinkedList<>();
        listSelectedPuntosEntrega.stream().forEach((row) -> {
            PedidosDestinos pedidosDestinos = new PedidosDestinos();
            pedidosDestinos.setPedidos(selected);
            pedidosDestinos.setDirecciones(row.getDirecciones());
            pedidosDestinos.setFechaActualizacion(new Date());
            pedidosDestinos.setUsuarioActualizacion("csarmiento");
            pedidosDestinos.setPuntosEntrega(row);
            pedidosDestinos.setValorDeclarado(BigDecimal.ZERO);
            listPedidosDestinos.add(pedidosDestinos);
            listItemPuntosEntrega.add(new SelectItem(row.getId(), row.getDirecciones().getDireccionEstandarizada()));
        });
    }

    public void addProductoAction(ActionEvent event) {
        PedidosProductos pedidosProductos = new PedidosProductos();
        pedidosProductos.setProductos(mapProductos.get(idProductos));
        pedidosProductos.setUoM(mapUoM.get(idUoM));
        pedidosProductos.setCantidadSolicitada(cantidadSolicitada);
        pedidosProductos.setCantidadDespachada(0);
        pedidosProductos.setValorDeclaradoPorUoMDespachada(BigDecimal.ZERO);
        listPedidosProductos.add(pedidosProductos);
    }

    public void removeProductoAction() {
        listSelectedPedidosProductos.stream().forEach((row) -> {
            if (Objects.equals(row.getPedidosDestinos().getPuntosEntrega().getId(), idPuntoEntrega)) {
                listPedidosProductosPE.remove(row);
            }
            listPedidosProductos.remove(row);
        });
        modelPedidosProductos = new DataModelCustom<>(listPedidosProductosPE);
    }

    public void destinatariosHandleChange(ValueChangeEvent event) {
        Integer id = (Integer) event.getNewValue();
        if (id != null) {
            destinatarios = mapDestinatarios.get(id);
            emailsContacto = destinatarios.getEmailsContacto();
            telefonosContacto = destinatarios.getTelefonosContacto();
        }
    }

    public void puntoEntregaHandleChange(ValueChangeEvent event) {
        try {
            Integer id = (Integer) event.getNewValue();
            listPedidosProductosPE = new LinkedList<>();
            if (id != null) {
                listPedidosProductos.stream().forEach((row) -> {
                    if (Objects.equals(row.getPedidosDestinos().getPuntosEntrega().getId(), id)) {
                        listPedidosProductosPE.add(row);
                    }
                });
            }
            modelPedidosProductos = new DataModelCustom<>(listPedidosProductosPE);
        } catch (Exception ex) {
            Logger.getLogger(PedidosBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
