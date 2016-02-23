package com.tacticlogistics.crm.view.backing;

import com.tacticlogistics.crm.model.bo.DashboardBO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.context.annotation.Scope;

import com.tacticlogistics.crm.view.util.DataModelCustom;
import com.tacticlogistics.crm.model.entities.Ciudades;
import com.tacticlogistics.crm.model.entities.dashboard.Inventario;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

@Named
@Scope("view")
public class InventarioBacking extends BaseBacking {

    @Inject
    private DashboardBO dashboardBO;

    private MapModel simpleModel;
    private Marker marker;

    private DataModelCustom<Inventario> inventarioModel;
    private Inventario inventario;

    private DataModelCustom<Inventario> inventarioDetalleModel;
    private Inventario inventarioDetalle;

    private Integer annio;
    private Integer mes;

    private Integer unitQuantitySum;
    private Integer maximumCapacitySum;
    private Integer usedCapacitySum;

    public InventarioBacking() {
        annio = 2015;
        mes = 10;
    }

    @PostConstruct
    public void init() {
        refreshAction();
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public void setSimpleModel(MapModel simpleModel) {
        this.simpleModel = simpleModel;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public DataModelCustom<Inventario> getInventarioModel() {
        return inventarioModel;
    }

    public void setInventarioModel(DataModelCustom<Inventario> inventarioModel) {
        this.inventarioModel = inventarioModel;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public DataModelCustom<Inventario> getInventarioDetalleModel() {
        return inventarioDetalleModel;
    }

    public void setInventarioDetalleModel(DataModelCustom<Inventario> inventarioDetalleModel) {
        this.inventarioDetalleModel = inventarioDetalleModel;
    }

    public Inventario getInventarioDetalle() {
        return inventarioDetalle;
    }

    public void setInventarioDetalle(Inventario inventarioDetalle) {
        this.inventarioDetalle = inventarioDetalle;
    }

    public Integer getAnnio() {
        return annio;
    }

    public void setAnnio(Integer annio) {
        this.annio = annio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getUnitQuantitySum() {
        return unitQuantitySum;
    }

    public void setUnitQuantitySum(Integer unitQuantitySum) {
        this.unitQuantitySum = unitQuantitySum;
    }

    public Integer getMaximumCapacitySum() {
        return maximumCapacitySum;
    }

    public void setMaximumCapacitySum(Integer maximumCapacitySum) {
        this.maximumCapacitySum = maximumCapacitySum;
    }

    public Integer getUsedCapacitySum() {
        return usedCapacitySum;
    }

    public void setUsedCapacitySum(Integer usedCapacitySum) {
        this.usedCapacitySum = usedCapacitySum;
    }

    public void refreshAction() {
        try {
            List<Inventario> list = dashboardBO.getListInventarioDetalle(annio, mes);
            inventarioModel = new DataModelCustom<>(list);

            simpleModel = new DefaultMapModel();
            unitQuantitySum = 0;
            usedCapacitySum = 0;
            maximumCapacitySum = 0;

            Map<Integer, Ciudades> mapCiudadesResumen = new HashMap<>();
            for (Inventario row : list) {
                unitQuantitySum = unitQuantitySum + row.getUnitQuantity();
                usedCapacitySum = usedCapacitySum + row.getUsedCapacity();
                maximumCapacitySum = maximumCapacitySum + row.getMaximumCapacity();
                mapCiudadesResumen.put(row.getId(), row.getCiudades());
            }

            mapCiudadesResumen.entrySet().stream().forEach((entry) -> {
                LatLng coord1 = new LatLng(entry.getValue().getLongitud().doubleValue(), entry.getValue().getLatitud().doubleValue());
                simpleModel.addOverlay(new Marker(coord1, entry.getValue().getNombre()));
            });
        } catch (Exception ex) {
            Logger.getLogger(DashboardBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Ciudad Seleccionada", marker.getTitle()));
    }

}
