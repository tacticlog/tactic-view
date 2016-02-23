package com.tacticlogistics.crm.view.backing;

import com.tacticlogistics.crm.model.entities.Ciudades;
import com.tacticlogistics.crm.model.entities.EstadosSolicitud;
import com.tacticlogistics.crm.model.entities.Solicitudes;
import com.tacticlogistics.crm.view.util.DataModelCustom;
import com.tacticlogistics.crm.view.util.JSFUtil;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;

import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.context.annotation.Scope;

@Named
@Scope("view")
public class SolicitudesBacking extends BaseBacking {

    private Solicitudes entity;
    private List<Solicitudes> listEntity;
    private DataModelCustom<Solicitudes> datamodel;
    private MapModel emptyModel;

    private List<SelectItem> listMunicipios;

    private String codMunicipio;
    private String nomMunicipio;
    private String codDepartamento;
    private String nomDepartamento;
    private String center;
    private String nuevaDireccion;
    private int zoom;

    private Integer estado;
    private Integer tipoSolicitud;

    @PostConstruct
    public void init() {
        entity = new Solicitudes();
        estado = 1;
        zoom = 10;
        tipoSolicitud = 1;
    }

    public Solicitudes getEntity() {
        return entity;
    }

    public void setEntity(Solicitudes entity) {
        this.entity = entity;
    }

    public List<Solicitudes> getListEntity() {
        return listEntity;
    }

    public void setListEntity(List<Solicitudes> listEntity) {
        this.listEntity = listEntity;
    }

    public DataModelCustom<Solicitudes> getDatamodel() {
        return datamodel;
    }

    public void setDatamodel(DataModelCustom<Solicitudes> datamodel) {
        this.datamodel = datamodel;
    }

    public MapModel getEmptyModel() {
        return emptyModel;
    }

    public void setEmptyModel(MapModel emptyModel) {
        this.emptyModel = emptyModel;
    }

    public List<SelectItem> getListMunicipios() {
        return listMunicipios;
    }

    public void setListMunicipios(List<SelectItem> listMunicipios) {
        this.listMunicipios = listMunicipios;
    }

    public String getCodMunicipio() {
        return codMunicipio;
    }

    public void setCodMunicipio(String codMunicipio) {
        this.codMunicipio = codMunicipio;
    }

    public String getNomMunicipio() {
        return nomMunicipio;
    }

    public void setNomMunicipio(String nomMunicipio) {
        this.nomMunicipio = nomMunicipio;
    }

    public String getCodDepartamento() {
        return codDepartamento;
    }

    public void setCodDepartamento(String codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    public String getNomDepartamento() {
        return nomDepartamento;
    }

    public void setNomDepartamento(String nomDepartamento) {
        this.nomDepartamento = nomDepartamento;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(Integer tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public String getNuevaDireccion() {
        return nuevaDireccion;
    }

    public void setNuevaDireccion(String nuevaDireccion) {
        this.nuevaDireccion = nuevaDireccion;
    }

    public void searchAction(ActionEvent event) {
        try {
            entity = new Solicitudes();
            if (idCiudades != null) {
                codMunicipio = mapCiudades.get(idCiudades).getCodigo();
                nomMunicipio = mapCiudades.get(idCiudades).getNombre();
                center = mapCiudades.get(idCiudades).getLongitud() + "," + mapCiudades.get(idCiudades).getLatitud();
                listEntity = catalogoBO.getListSolicitudes(codMunicipio, estado, tipoSolicitud);
            } else {
                codDepartamento = mapDepartamentos.get(idDepartamento).getCodigo();
                nomDepartamento = mapDepartamentos.get(idDepartamento).getNombre();
                listEntity = catalogoBO.getListSolicitudesPorDepartamento(codDepartamento, estado, tipoSolicitud);
            }

            datamodel = new DataModelCustom<>(listEntity);
            emptyModel = new DefaultMapModel();
            zoom = 10;
        } catch (Exception ex) {
            Logger.getLogger(SolicitudesBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void searchDetailAction() {
        try {
            nuevaDireccion = entity.getDireccion();
            nomMunicipio = entity.getNombreMunicipio();
            codMunicipio = entity.getCodigoMunicipio();
            if (entity.getDireccionSugeridaGeoReferenciacionManual().isEmpty()) {
                entity.setDireccionSugeridaGeoReferenciacionManual(entity.getDireccion());
            }
            Ciudades ciudades = catalogoBO.getObjectCiudadesPorCodigo(entity.getCodigoMunicipio());
            if (entity.getCx().doubleValue() == 0) {
                center = ciudades.getLongitud() + "," + ciudades.getLatitud();
                zoom = 12;
            } else {
                center = entity.getCy() + "," + entity.getCx();
                LatLng latLng = new LatLng(entity.getCy().doubleValue(), entity.getCx().doubleValue());
                Marker marker = new Marker(latLng);
                emptyModel = new DefaultMapModel();
                emptyModel.getMarkers().add(marker);
                zoom = 17;
            }
        } catch (Exception ex) {
            Logger.getLogger(SolicitudesBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateAction(ActionEvent event) {
        try {
            entity.setIdTipoGeoReferenciacion(3);
            entity.setEstadosSolicitudes(new EstadosSolicitud(3));
            entity.setUsuarioActualizacion("AMBEV");
            entity.setUsuarioGeoReferenciacionManual("AMBEV");
            entity.setFechaGeoReferenciacionManual(new Date());
            entity.setFechaActualizacion(new Date());
            catalogoBO.save(entity);
            searchAction(null);
            JSFUtil.addInfo("Operación Exitosa!");
        } catch (Exception ex) {
            JSFUtil.addError(ex.getMessage());
        }
    }

    public void onGeocode(GeocodeEvent event) {
        List<GeocodeResult> results = event.getResults();
        if (results != null && !results.isEmpty()) {
            LatLng cen = results.get(0).getLatLng();
            center = cen.getLat() + "," + cen.getLng();
            zoom = 15;
        }
    }

}
