package com.tacticlogistics.crm.view.backing;

import java.util.List;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import com.tacticlogistics.crm.model.bo.CatalogoBO;
import com.tacticlogistics.crm.model.bo.ClientesBO;
import com.tacticlogistics.crm.model.bo.PedidosBO;
import com.tacticlogistics.crm.model.bo.ProductosBO;
import com.tacticlogistics.crm.model.entities.CentrosDistribucion;
import com.tacticlogistics.crm.model.entities.Ciudades;
import com.tacticlogistics.crm.model.entities.Clientes;
import com.tacticlogistics.crm.model.entities.Departamentos;
import com.tacticlogistics.crm.model.entities.Destinatarios;
import com.tacticlogistics.crm.model.entities.EstadosPedidos;
import com.tacticlogistics.crm.model.entities.EstadosSolicitud;
import com.tacticlogistics.crm.model.entities.Paises;
import com.tacticlogistics.crm.model.entities.PrioridadesPedidos;
import com.tacticlogistics.crm.model.entities.Productos;
import com.tacticlogistics.crm.model.entities.ProductosUoM;
import com.tacticlogistics.crm.model.entities.PuntosEntrega;
import com.tacticlogistics.crm.model.entities.TiposFormaPago;
import com.tacticlogistics.crm.model.entities.TiposPedido;
import com.tacticlogistics.crm.model.entities.TiposSolicitud;
import com.tacticlogistics.crm.model.entities.UoM;
import com.tacticlogistics.crm.model.entities.app.Menus;
import com.tacticlogistics.crm.view.util.DataModelCustom;
import com.tacticlogistics.crm.view.util.JSFUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;

public class BaseBacking {

    /**
     *
     */
    @Inject
    protected CatalogoBO catalogoBO;

    @Inject
    protected ClientesBO clientesBO;

    @Inject
    protected ProductosBO productosBO;

    @Inject
    protected PedidosBO pedidosBO;

    protected Integer idClientes;
    protected Integer idPais;
    protected Integer idDepartamento;
    protected Integer idCiudades;
    protected Integer idProductos;
    protected Integer idUoM;
    protected Integer idCentrosDistribucion;

    protected Map<Integer, Departamentos> mapDepartamentos;
    protected Map<Integer, Ciudades> mapCiudades;
    protected Map<Integer, Productos> mapProductos;
    protected Map<Integer, UoM> mapUoM;
    protected Map<Integer, Destinatarios> mapDestinatarios;
    protected Map<Integer, CentrosDistribucion> mapCentrosDistribucion;

    protected List<SelectItem> listEstadosPedidos;
    protected List<SelectItem> listPrioridadesPedidos;
    protected List<SelectItem> listTiposFormaPago;
    protected List<SelectItem> listTiposPedido;
    protected List<SelectItem> listClientesActivos;
    protected List<SelectItem> listCiudades;

    protected String parametroProducto;
    protected List<ProductosUoM> listProductosTemp;
    protected DataModelCustom<ProductosUoM> modelProductosTemp;
    protected ProductosUoM selectedProductosTemp;

    public String getParametroProducto() {
        return parametroProducto;
    }

    public void setParametroProducto(String parametroProducto) {
        this.parametroProducto = parametroProducto;
    }

    public List<ProductosUoM> getListProductosTemp() {
        return listProductosTemp;
    }

    public void setListProductosTemp(List<ProductosUoM> listProductosTemp) {
        this.listProductosTemp = listProductosTemp;
    }

    public DataModelCustom<ProductosUoM> getModelProductosTemp() {
        return modelProductosTemp;
    }

    public void setModelProductosTemp(DataModelCustom<ProductosUoM> modelProductosTemp) {
        this.modelProductosTemp = modelProductosTemp;
    }

    public ProductosUoM getSelectedProductosTemp() {
        return selectedProductosTemp;
    }

    public void setSelectedProductosTemp(ProductosUoM selectedProductosTemp) {
        this.selectedProductosTemp = selectedProductosTemp;
    }

    public Integer getIdClientes() {
        return idClientes;
    }

    public void setIdClientes(Integer idClientes) {
        this.idClientes = idClientes;
    }

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Integer getIdCiudades() {
        return idCiudades;
    }

    public void setIdCiudades(Integer idCiudades) {
        this.idCiudades = idCiudades;
    }

    public Integer getIdProductos() {
        return idProductos;
    }

    public void setIdProductos(Integer idProductos) {
        this.idProductos = idProductos;
    }

    public Integer getIdUoM() {
        return idUoM;
    }

    public void setIdUoM(Integer idUoM) {
        this.idUoM = idUoM;
    }

    public Integer getIdCentrosDistribucion() {
        return idCentrosDistribucion;
    }

    public void setIdCentrosDistribucion(Integer idCentrosDistribucion) {
        this.idCentrosDistribucion = idCentrosDistribucion;
    }

    public void searchProductoPorClienteAction(ActionEvent event) {
        selectedProductosTemp = null;
        if (JSFUtil.isEmptyOrBlank(parametroProducto)) {
            String message = JSFUtil.getMessage("msg_required_fields");
            JSFUtil.addWarn(message);
            listProductosTemp = new LinkedList<>();
        } else {
            try {
                listProductosTemp = productosBO.getListProductosUoMPorClientes(idClientes,
                        this.parametroProducto);
            } catch (Exception ex) {
                Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        modelProductosTemp = new DataModelCustom(listProductosTemp);
    }

    public List<SelectItem> getListCiudades() {
        try {
            mapCiudades = new HashMap<>();
            listCiudades = new LinkedList<>();
            List<Ciudades> list = catalogoBO.getListCiudades();
            list.stream().forEach((value) -> {
                mapCiudades.put(value.getId(), value);
                listCiudades.add(new SelectItem(value.getId(), value.getDepartamentos().getNombre() + "- " + value.getNombre()));
            });
            return listCiudades;
        } catch (Exception ex) {
            Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<SelectItem> getListPuntosEntregaPorDestinatario(Integer idDestinatario) {
        try {
            List<SelectItem> result = new LinkedList<>();
            if (idDestinatario != null) {
                List<PuntosEntrega> list = clientesBO.getListPuntosEntregaPorDestinatario(idDestinatario);
                list.stream().forEach((value) -> {
                    result.add(new SelectItem(value.getId(), value.getDirecciones().getDireccionEstandarizada()));
                });
            }
            return result;
        } catch (Exception ex) {
            Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<SelectItem> getListProductosPorCliente() {
        try {
            List<SelectItem> result = new LinkedList<>();
            if (idClientes != null) {
                List<Productos> list = productosBO.getListProductosPorCliente(idClientes);
                mapProductos = new HashMap<>();
                list.stream().forEach((value) -> {
                    mapProductos.put(value.getId(), value);
                    result.add(new SelectItem(value.getId(), value.getNombreLargo()));
                });
            }
            return result;
        } catch (Exception ex) {
            Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<SelectItem> getListUoMPorProductos(Integer idProductos) {
        try {
            List<SelectItem> result = new LinkedList<>();
            mapUoM = new HashMap<>();
            if (idProductos != null) {
                List<UoM> list = productosBO.getListUoMPorProductos(idProductos);
                list.stream().forEach((value) -> {
                    mapUoM.put(value.getId(), value);
                    result.add(new SelectItem(value.getId(), value.getNombre()));
                });
            }
            return result;
        } catch (Exception ex) {
            Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<SelectItem> getListTiposFormaPago() {
        try {
            if (listTiposFormaPago == null) {
                listTiposFormaPago = new LinkedList<>();
                List<TiposFormaPago> list = catalogoBO.getListTiposFormaPago();
                list.stream().forEach((value) -> {
                    listTiposFormaPago.add(new SelectItem(value.getId(), value.getNombre()));
                });
            }
            return listTiposFormaPago;
        } catch (Exception ex) {
            Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<SelectItem> getListTiposPedido() {
        try {
            if (listTiposPedido == null) {
                listTiposPedido = new LinkedList<>();
                List<TiposPedido> list = catalogoBO.getListTiposPedido();
                list.stream().forEach((value) -> {
                    listTiposPedido.add(new SelectItem(value.getId(), value.getNombre()));
                });
            }
            return listTiposPedido;
        } catch (Exception ex) {
            Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<SelectItem> getListPrioridadesPedidos() {
        try {
            if (listPrioridadesPedidos == null) {
                listPrioridadesPedidos = new LinkedList<>();
                List<PrioridadesPedidos> list = catalogoBO.getListPrioridadesPedidos();
                list.stream().forEach((value) -> {
                    listPrioridadesPedidos.add(new SelectItem(value.getId(), value.getNombre()));
                });
            }
            return listPrioridadesPedidos;
        } catch (Exception ex) {
            Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<SelectItem> getListEstadosPedidos() {
        try {
            if (listEstadosPedidos == null) {
                listEstadosPedidos = new LinkedList<>();
                List<EstadosPedidos> list = catalogoBO.getListEstadosPedidos();
                listEstadosPedidos.add(new SelectItem(0, "-- Todos --"));
                list.stream().forEach((value) -> {
                    listEstadosPedidos.add(new SelectItem(value.getId(), value.getNombre()));
                });
            }
            return listEstadosPedidos;
        } catch (Exception ex) {
            Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Menus> getMenus() {
        try {
            List<Menus> list = catalogoBO.getListMenus();
            return list;
        } catch (Exception ex) {
            Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<SelectItem> getListClientesActivos() {
        try {
            if (listClientesActivos == null) {
                listClientesActivos = new LinkedList<>();
                List<Clientes> list = clientesBO.getListClientesActivos();
                list.stream().forEach((value) -> {
                    listClientesActivos.add(new SelectItem(value.getId(), value.getTerceros().toString()));
                });
            }
            return listClientesActivos;
        } catch (Exception ex) {
            Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<SelectItem> getListDestinatarioPorCliente(Integer idClientes) {
        try {
            List<SelectItem> result = new LinkedList<>();
            mapDestinatarios = new HashMap<>();
            if (idClientes != null) {
                List<Destinatarios> list = clientesBO.getListDestinatarioPorCliente(idClientes);
                list.stream().forEach((value) -> {
                    mapDestinatarios.put(value.getId(), value);
                    result.add(new SelectItem(value.getId(), value.getTerceros().toString()));
                });
            }
            return result;
        } catch (Exception ex) {
            Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<SelectItem> getListCentrosDistribucion() {
        try {
            List<SelectItem> result = new LinkedList<>();
            mapCentrosDistribucion = new HashMap<>();
            List<CentrosDistribucion> list = clientesBO.getListCentrosDistribucion();
            list.stream().forEach((value) -> {
                mapCentrosDistribucion.put(value.getId(), value);
                result.add(new SelectItem(value.getId(), value.getNombre()));
            });
            return result;
        } catch (Exception ex) {
            Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Date getToday() {
        return new Date();
    }

    public List<SelectItem> getItemsPaises() {
        try {
            List<Paises> list = catalogoBO.getListPaisesActivo();
            List<SelectItem> items = new ArrayList<>(list.size());
            list.stream().forEach((value) -> {
                items.add(new SelectItem(value.getId(), value.getNombre()));
            });

            if (!items.isEmpty()) {
                idPais = (Integer) items.get(0).getValue();
            }

            return items;
        } catch (Exception ex) {
            Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<SelectItem> getItemsDepartamentos(Integer paisId) {
        try {
            mapDepartamentos = new HashMap<>();
            List<Departamentos> list = catalogoBO.getListDepartamentosActivoPorPais(paisId);
            List<SelectItem> items = new ArrayList<>(list.size());
            list.stream().map((value) -> {
                items.add(new SelectItem(value.getId(), value.getNombre()));
                return value;
            }).forEach((value) -> {
                mapDepartamentos.put(value.getId(), value);
            });

            return items;
        } catch (Exception ex) {
            Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<SelectItem> getItemsCiudades(Integer departamentoId) {
        try {
            mapCiudades = new HashMap<>();
            List<Ciudades> list = catalogoBO.getListCiudadesActivoPorDepartamentos(departamentoId);
            List<SelectItem> items = new ArrayList<>(list.size());
            list.stream().map((value) -> {
                items.add(new SelectItem(value.getId(), value.getNombre()));
                return value;
            }).forEach((value) -> {
                mapCiudades.put(value.getId(), value);
            });
            return items;
        } catch (Exception ex) {
            Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<SelectItem> getItemsEstadosSolicitud() {
        try {
            List<EstadosSolicitud> list = catalogoBO.getListEstadosSolicitudActivo();
            List<SelectItem> items = new ArrayList<>(list.size());
            list.stream().forEach((value) -> {
                items.add(new SelectItem(value.getId(), value.getNombre()));
            });
            return items;
        } catch (Exception ex) {
            Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<SelectItem> getItemsTiposSolicitud() {
        try {
            List<TiposSolicitud> list = catalogoBO.getListTiposSolicitudActivo();
            List<SelectItem> items = new ArrayList<>(list.size());
            list.stream().forEach((value) -> {
                items.add(new SelectItem(value.getId(), value.getNombre()));
            });
            return items;
        } catch (Exception ex) {
            Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*private List<SelectItem> listTiposIdentificacion;
    private List<SelectItem> listTiposFormaPago;
    private List<SelectItem> listPrioridadesOrdenCompra;
    private List<SelectItem> listEstadosOrdenCompra;

    protected String parametro;
    protected List<Clientes> listClientesTemp;
    protected DataModelCustom<Clientes> modelClientesTemp;
    protected Clientes clientesTemp;
    protected List<Terceros> listClientesTerceroTemp;
    protected DataModelCustom<Terceros> modelClientesTerceroTemp;
    protected Terceros clientesTerceroTemp;

    protected List<ProductosUoM> listProductosUoMTemp;
    protected ProductosUoModel modelProductosUoModelTemp;
    protected String parametroProducto;
    protected ProductosUoM productosUoMTemp;

    protected Map<Integer, Ciudades> mapCiudades;

    public BaseBacking() {
        clientesTemp = new Clientes();
        clientesTerceroTemp = new Terceros();
    }

    public List<SelectItem> getItemsTiposIdentificacion() {
        List<TiposIdentificacion> list = catalogoBO.getListTiposIdentificacionActivo();
        List<SelectItem> items = new ArrayList<>(list.size());
        for (TiposIdentificacion value : list) {
            items.add(new SelectItem(value.getId(), value.getNombre()));
        }
        return items;
    }

    public List<SelectItem> getItemsTiposSaludoTercero() {
        List<TiposSaludoTercero> list = catalogoBO.getListTiposSaludoTerceroActivo();
        List<SelectItem> items = new ArrayList<>(list.size());
        for (TiposSaludoTercero value : list) {
            items.add(new SelectItem(value.getId(), value.getNombre()));
        }
        return items;
    }

    protected List<SelectItem> getItemsTiposDireccion() {
        List<TiposDireccion> list = catalogoBO.getListTiposDireccionActivo();
        List<SelectItem> items = new ArrayList<>(list.size());
        for (TiposDireccion value : list) {
            items.add(new SelectItem(value.getId(), value.getNombre()));
        }
        return items;
    }

    public List<SelectItem> getItemsPaises() {
        List<Paises> list = catalogoBO.getListPaisesActivo();
        List<SelectItem> items = new ArrayList<>(list.size());
        for (Paises value : list) {
            items.add(new SelectItem(value.getId(), value.getNombre()));
        }
        return items;
    }

    public List<SelectItem> getItemsDepartamentos(Integer paisId) {
        List<Departamentos> list = catalogoBO.getListDepartamentosActivoPorPais(paisId);
        List<SelectItem> items = new ArrayList<>(list.size());
        for (Departamentos value : list) {
            items.add(new SelectItem(value.getId(), value.getNombre()));
        }
        return items;
    }

    public List<SelectItem> getItemsCiudades(Integer departamentoId) {
        mapCiudades = new HashMap<>();
        List<Ciudades> list = catalogoBO.getListCiudadesActivoPorDepartamentos(departamentoId);
        List<SelectItem> items = new ArrayList<>(list.size());
        for (Ciudades value : list) {
            items.add(new SelectItem(value.getId(), value.getNombre()));
            mapCiudades.put(value.getId(), value);
        }
        return items;
    }

    public List<SelectItem> getItemsCodigoCiudades(Integer departamentoId) {
        List<Ciudades> list = catalogoBO.getListCiudadesActivoPorDepartamentos(departamentoId);
        List<SelectItem> items = new ArrayList<>(list.size());
        for (Ciudades value : list) {
            items.add(new SelectItem(value.getCodigo(), value.getNombre()));
        }
        return items;
    }

    protected List<SelectItem> getItemsZonas() {
        List<Zonas> list = catalogoBO.getListZonasActivo();
        List<SelectItem> items = new ArrayList<>(list.size());
        for (Zonas value : list) {
            items.add(new SelectItem(value.getId(), value.getNombre()));
        }
        return items;
    }

    protected List<SelectItem> getTiposFormaPago() {
        List<TiposFormaPago> list = catalogoBO.getTiposFormaPago();
        List<SelectItem> items = new ArrayList<>(list.size());
        for (TiposFormaPago value : list) {
            items.add(new SelectItem(value.getId(), value.getNombre()));
        }
        return items;
    }

    protected List<SelectItem> getEstadosOrdenCompra() {
        List<EstadosOrdenCompra> list = catalogoBO.getListEstadosOrdenCompra();
        List<SelectItem> items = new ArrayList<>(list.size());
        items.add(new SelectItem(0, "-- Todos --"));
        for (EstadosOrdenCompra value : list) {
            items.add(new SelectItem(value.getId(), value.getNombre()));
        }
        return items;
    }

    protected List<SelectItem> getPrioridadesOrdenCompra() {
        List<PrioridadesOrdenCompra> list = catalogoBO.getPrioridadesOrdenCompra();
        List<SelectItem> items = new ArrayList<>(list.size());
        for (PrioridadesOrdenCompra value : list) {
            items.add(new SelectItem(value.getId(), value.getNombre()));
        }
        return items;
    }

    protected List<Clientes> getLisClientes() {
        List<Clientes> list = catalogoBO.getListClientes();
        return list;
    }

    public List<SelectItem> getListTiposIdentificacion() {
        if (listTiposIdentificacion == null) {
            listTiposIdentificacion = getItemsTiposIdentificacion();
        }
        return listTiposIdentificacion;
    }

    public void setListTiposIdentificacion(List<SelectItem> listTiposIdentificacion) {
        this.listTiposIdentificacion = listTiposIdentificacion;
    }

    public List<SelectItem> getListTiposFormaPago() {
        if (listTiposFormaPago == null) {
            listTiposFormaPago = getTiposFormaPago();
        }
        return listTiposFormaPago;
    }

    public void setListTiposFormaPago(List<SelectItem> listTiposFormaPago) {
        this.listTiposFormaPago = listTiposFormaPago;
    }

    public List<SelectItem> getListPrioridadesOrdenCompra() {
        if (listPrioridadesOrdenCompra == null) {
            listPrioridadesOrdenCompra = getPrioridadesOrdenCompra();
        }
        return listPrioridadesOrdenCompra;
    }

    public void setListPrioridadesOrdenCompra(List<SelectItem> listPrioridadesOrdenCompra) {
        this.listPrioridadesOrdenCompra = listPrioridadesOrdenCompra;
    }

    public List<SelectItem> getListEstadosOrdenCompra() {
        if (listEstadosOrdenCompra == null) {
            listEstadosOrdenCompra = getEstadosOrdenCompra();
        }
        return listEstadosOrdenCompra;
    }

    public void setListEstadosOrdenCompra(List<SelectItem> listEstadosOrdenCompra) {
        this.listEstadosOrdenCompra = listEstadosOrdenCompra;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public List<Clientes> getListClientesTemp() {
        return listClientesTemp;
    }

    public void setListClientesTemp(List<Clientes> listClientesTemp) {
        this.listClientesTemp = listClientesTemp;
    }

    public DataModelCustom<Clientes> getModelClientesTemp() {
        return modelClientesTemp;
    }

    public void setModelClientesTemp(DataModelCustom<Clientes> modelClientesTemp) {
        this.modelClientesTemp = modelClientesTemp;
    }

    public Clientes getClientesTemp() {
        return clientesTemp;
    }

    public void setClientesTemp(Clientes clientesTemp) {
        this.clientesTemp = clientesTemp;
    }

    public ClientesTercero getClientesTerceroTemp() {
        return clientesTerceroTemp;
    }

    public void setClientesTerceroTemp(ClientesTercero clientesTerceroTemp) {
        this.clientesTerceroTemp = clientesTerceroTemp;
    }

    public List<ClientesTercero> getListClientesTerceroTemp() {
        return listClientesTerceroTemp;
    }

    public void setListClientesTerceroTemp(List<ClientesTercero> listClientesTerceroTemp) {
        this.listClientesTerceroTemp = listClientesTerceroTemp;
    }

    public DataModelCustom<ClientesTercero> getModelClientesTerceroTemp() {
        return modelClientesTerceroTemp;
    }

    public void setModelClientesTerceroTemp(DataModelCustom<ClientesTercero> modelClientesTerceroTemp) {
        this.modelClientesTerceroTemp = modelClientesTerceroTemp;
    }

    public List<ProductosUoM> getListProductosUoMTemp() {
        return listProductosUoMTemp;
    }

    public void setListProductosUoMTemp(List<ProductosUoM> listProductosUoMTemp) {
        this.listProductosUoMTemp = listProductosUoMTemp;
    }

    public ProductosUoModel getModelProductosUoModelTemp() {
        return modelProductosUoModelTemp;
    }

    public void setModelProductosUoModelTemp(ProductosUoModel modelProductosUoModelTemp) {
        this.modelProductosUoModelTemp = modelProductosUoModelTemp;
    }

    public String getParametroProducto() {
        return parametroProducto;
    }

    public void setParametroProducto(String parametroProducto) {
        this.parametroProducto = parametroProducto;
    }

    public ProductosUoM getProductosUoMTemp() {
        return productosUoMTemp;
    }

    public void setProductosUoMTemp(ProductosUoM productosUoMTemp) {
        this.productosUoMTemp = productosUoMTemp;
    }

    public void save(Object entity) {
        catalogoBO.save(entity);
    }

    public void searchCliente(ActionEvent event) {
        clientesTemp = null;
        if (JSFUtil.isEmptyOrBlank(parametro)) {
            String message = JSFUtil.getMessage("msg_required_fields");
            JSFUtil.addWarn(message);
            listClientesTemp = new LinkedList<>();
        } else {
            listClientesTemp = catalogoBO.getListClientesPorCriterios(parametro);
        }
        modelClientesTemp = new DataModelCustom<>(listClientesTemp);
    }

    public void searchClienteTercero(ActionEvent event) {
        clientesTerceroTemp = null;
        if (JSFUtil.isEmptyOrBlank(parametro)) {
            String message = JSFUtil.getMessage("msg_required_fields");
            JSFUtil.addWarn(message);
            listClientesTerceroTemp = new LinkedList<>();
        } else {
            listClientesTerceroTemp = catalogoBO.getListClientesTerceroPorCriterios(parametro);
        }
        modelClientesTerceroTemp = new DataModelCustom<>(listClientesTerceroTemp);
    }

    public void searchProductoPorClienteAction(ActionEvent event) {
        productosUoMTemp = null;
        if (JSFUtil.isEmptyOrBlank(parametroProducto)) {
            String message = JSFUtil.getMessage("msg_required_fields");
            JSFUtil.addWarn(message);
            listProductosUoMTemp = new LinkedList<>();
        } else {
            listProductosUoMTemp = catalogoBO.getListProductosUoMPorClientesTercero(JSFUtil.idCliente,
                    this.parametroProducto);
        }
        modelProductosUoModelTemp = new ProductosUoModel(listProductosUoMTemp);
    }

    public List<OrdenesCompra> getListOrdenCompra(Integer estadosOrdenCompraId, Date fechaInicial, Date fechaFinal) {
        String fechaInicialString = JSFUtil.formatDate(fechaInicial, "yyyy/MM/dd") + " 00:00:00";
        String fechaFinalString = JSFUtil.formatDate(fechaFinal, "yyyy/MM/dd") + " 23:59:59";
        return catalogoBO.getListOrdenCompra(estadosOrdenCompraId, fechaInicialString, fechaFinalString);
    }

    public List<Resultados> getListResultado(Integer annio, Integer mes) {
        return catalogoBO.getListResultado(annio, mes);
    }

    public List<Resultados> getListResultado(String kpi, Integer annio, Integer mes) {
        return catalogoBO.getListResultado(kpi, annio, mes);
    }

    public Resultados getResultadoPorKPI(String kpi, Integer annio, Integer mes) {
        return catalogoBO.getResultadoPorKPI(kpi, annio, mes);
    }

    public List<Inventario> getListInventario(Integer annio, Integer mes) {
        return catalogoBO.getListInventario(annio, mes);
    }

    

    public List<Solicitudes> getListSolicitudes(String codMunicipio, Integer estado) {
        return catalogoBO.getListSolicitudes(codMunicipio, estado);
    }*/
}
