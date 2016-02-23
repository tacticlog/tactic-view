/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tacticlogistics.crm.view.backing;

import com.tacticlogistics.crm.model.bo.RadicacionBO;
import com.tacticlogistics.crm.model.entities.radicacion.ArchivosRadicados;
import com.tacticlogistics.crm.model.entities.radicacion.CamposArchivoRadicado;
import com.tacticlogistics.crm.model.entities.radicacion.EstadosArchivoRadicado;
import com.tacticlogistics.crm.model.entities.radicacion.GruposArchivosRadicados;
import com.tacticlogistics.crm.model.entities.radicacion.OrdenesVentasSecundaria;
import com.tacticlogistics.crm.model.entities.radicacion.OrdenesVentasSecundariaLog;
import com.tacticlogistics.crm.model.entities.radicacion.OrdenesVentasSecundariaTexto;
import com.tacticlogistics.crm.model.entities.radicacion.TiposArchivoRadicado;
import com.tacticlogistics.crm.view.util.DataModelCustom;
import com.tacticlogistics.crm.view.util.JSFUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Column;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author csarmiento
 */
@Named
@Scope("view")
public class RadicacionBacking implements Serializable {

    @Inject
    private RadicacionBO radicacionBO;

    private GruposArchivosRadicados gruposArchivosRadicados;
    private List<SelectItem> gruposArchivosRadicadosItems;
    private Map<Integer, GruposArchivosRadicados> mapGruposArchivosRadicados;
    private Integer gruposArchivosRadicadosId;

    private TiposArchivoRadicado tiposArchivoRadicado;
    private List<SelectItem> tiposArchivoRadicadoItems;
    private Map<Integer, TiposArchivoRadicado> mapTiposArchivoRadicado;
    private Integer tiposArchivoRadicadoId;

    private List<CamposArchivoRadicado> camposArchivoRadicadoList;
    private List<OrdenesVentasSecundariaLog> listLog;
    private List<OrdenesVentasSecundariaTexto> listTexto;

    private boolean haveHeader;
    private String allowTypes;
    private InputStream inputStream;
    private String fileName;

    private static final Integer MAX_INTEGER = 2147483647;
    private static final Integer MAX_VARCHAR = 2147483647;

    private boolean finished;

    private Date today;
    private Date fechaInicial;
    private Date fechaFinal;
    private Integer idEstado;
    private List<SelectItem> listEstadosArchivoRadicado;
    private List<ArchivosRadicados> listArchivosRadicados;
    private DataModel<ArchivosRadicados> modelArchivosRadicados;
    private ArchivosRadicados selectedArchivosRadicados;
    private DataModel<OrdenesVentasSecundariaLog> modelLog;

    public RadicacionBacking() {

    }

    @PostConstruct
    public void init() {
        newAction(null);
        try {
            List<GruposArchivosRadicados> gruposArchivosRadicadosList = radicacionBO.getListGruposArchivosRadicados();
            mapGruposArchivosRadicados = new HashMap<>();
            gruposArchivosRadicadosItems = new LinkedList<>();
            tiposArchivoRadicadoItems = new LinkedList<>();
            gruposArchivosRadicadosList.stream().map((row) -> {
                mapGruposArchivosRadicados.put(row.getId(), row);
                return row;
            }).forEach((row) -> {
                gruposArchivosRadicadosItems.add(new SelectItem(row.getId(), row.getNombre()));
            });
        } catch (Exception ex) {
            Logger.getLogger(RadicacionBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public RadicacionBO getRadicacionBO() {
        return radicacionBO;
    }

    public void setRadicacionBO(RadicacionBO radicacionBO) {
        this.radicacionBO = radicacionBO;
    }

    public GruposArchivosRadicados getGruposArchivosRadicados() {
        return gruposArchivosRadicados;
    }

    public void setGruposArchivosRadicados(GruposArchivosRadicados gruposArchivosRadicados) {
        this.gruposArchivosRadicados = gruposArchivosRadicados;
    }

    public List<SelectItem> getGruposArchivosRadicadosItems() {
        return gruposArchivosRadicadosItems;
    }

    public void setGruposArchivosRadicadosItems(List<SelectItem> gruposArchivosRadicadosItems) {
        this.gruposArchivosRadicadosItems = gruposArchivosRadicadosItems;
    }

    public Integer getGruposArchivosRadicadosId() {
        return gruposArchivosRadicadosId;
    }

    public void setGruposArchivosRadicadosId(Integer gruposArchivosRadicadosId) {
        this.gruposArchivosRadicadosId = gruposArchivosRadicadosId;
    }

    public TiposArchivoRadicado getTiposArchivoRadicado() {
        return tiposArchivoRadicado;
    }

    public void setTiposArchivoRadicado(TiposArchivoRadicado tiposArchivoRadicado) {
        this.tiposArchivoRadicado = tiposArchivoRadicado;
    }

    public List<SelectItem> getTiposArchivoRadicadoItems() {
        return tiposArchivoRadicadoItems;
    }

    public void setTiposArchivoRadicadoItems(List<SelectItem> tiposArchivoRadicadoItems) {
        this.tiposArchivoRadicadoItems = tiposArchivoRadicadoItems;
    }

    public Map<Integer, TiposArchivoRadicado> getMapTiposArchivoRadicado() {
        return mapTiposArchivoRadicado;
    }

    public void setMapTiposArchivoRadicado(Map<Integer, TiposArchivoRadicado> mapTiposArchivoRadicado) {
        this.mapTiposArchivoRadicado = mapTiposArchivoRadicado;
    }

    public Integer getTiposArchivoRadicadoId() {
        return tiposArchivoRadicadoId;
    }

    public void setTiposArchivoRadicadoId(Integer tiposArchivoRadicadoId) {
        this.tiposArchivoRadicadoId = tiposArchivoRadicadoId;
    }

    public List<CamposArchivoRadicado> getCamposArchivoRadicadoList() {
        return camposArchivoRadicadoList;
    }

    public void setCamposArchivoRadicadoList(List<CamposArchivoRadicado> camposArchivoRadicadoList) {
        this.camposArchivoRadicadoList = camposArchivoRadicadoList;
    }

    public List<OrdenesVentasSecundariaLog> getListLog() {
        return listLog;
    }

    public void setListLog(List<OrdenesVentasSecundariaLog> listLog) {
        this.listLog = listLog;
    }

    public List<OrdenesVentasSecundariaTexto> getListTexto() {
        return listTexto;
    }

    public void setListTexto(List<OrdenesVentasSecundariaTexto> listTexto) {
        this.listTexto = listTexto;
    }

    public boolean isHaveHeader() {
        return haveHeader;
    }

    public void setHaveHeader(boolean haveHeader) {
        this.haveHeader = haveHeader;
    }

    public String getAllowTypes() {
        return allowTypes;
    }

    public void setAllowTypes(String allowTypes) {
        this.allowTypes = allowTypes;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public List<SelectItem> getListEstadosArchivoRadicado() {
        try {
            if (listEstadosArchivoRadicado == null) {
                listEstadosArchivoRadicado = new LinkedList<>();
                List<EstadosArchivoRadicado> list = radicacionBO.getListEstadosArchivoRadicado();
                listEstadosArchivoRadicado.add(new SelectItem(0, "-- Todos --"));
                list.stream().forEach((value) -> {
                    listEstadosArchivoRadicado.add(new SelectItem(value.getId(), value.getNombre()));
                });
            }
            return listEstadosArchivoRadicado;
        } catch (Exception ex) {
            Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void setListEstadosArchivoRadicado(List<SelectItem> listEstadosArchivoRadicado) {
        this.listEstadosArchivoRadicado = listEstadosArchivoRadicado;
    }

    public List<ArchivosRadicados> getListArchivosRadicados() {
        return listArchivosRadicados;
    }

    public void setListArchivosRadicados(List<ArchivosRadicados> listArchivosRadicados) {
        this.listArchivosRadicados = listArchivosRadicados;
    }

    public DataModel<ArchivosRadicados> getModelArchivosRadicados() {
        return modelArchivosRadicados;
    }

    public void setModelArchivosRadicados(DataModel<ArchivosRadicados> modelArchivosRadicados) {
        this.modelArchivosRadicados = modelArchivosRadicados;
    }

    public ArchivosRadicados getSelectedArchivosRadicados() {
        return selectedArchivosRadicados;
    }

    public void setSelectedArchivosRadicados(ArchivosRadicados selectedArchivosRadicados) {
        this.selectedArchivosRadicados = selectedArchivosRadicados;
    }

    public DataModel<OrdenesVentasSecundariaLog> getModelLog() {
        return modelLog;
    }

    public void setModelLog(DataModel<OrdenesVentasSecundariaLog> modelLog) {
        this.modelLog = modelLog;
    }

    public void changeGrupoArchivo() {
        try {
            allowTypes = "-1";
            List<TiposArchivoRadicado> tiposArchivoRadicadoList = radicacionBO.getListTiposArchivoRadicado(gruposArchivosRadicadosId);
            mapTiposArchivoRadicado = new HashMap<>();
            tiposArchivoRadicadoItems = new LinkedList<>();
            tiposArchivoRadicadoList.stream().map((row) -> {
                mapTiposArchivoRadicado.put(row.getId(), row);
                return row;
            }).forEach((row) -> {
                tiposArchivoRadicadoItems.add(new SelectItem(row.getId(), row.getNombre()));
            });
        } catch (Exception ex) {
            Logger.getLogger(RadicacionBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changeTipoArchivo() {
        try {
            tiposArchivoRadicado = mapTiposArchivoRadicado.get(tiposArchivoRadicadoId);
            String extensionArchivo = tiposArchivoRadicado.getExtensionArchivo().toLowerCase().replace(",", "|");
            allowTypes = "/(\\.|\\/)(" + extensionArchivo + ")$/";
        } catch (Exception ex) {
            Logger.getLogger(RadicacionBacking.class.getName()).log(Level.SEVERE, null, ex);
            allowTypes = null;
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            fileName = uploadedFile.getFileName();
            inputStream = uploadedFile.getInputstream();
            JSFUtil.addInfo("Archivo Cargado. Haga clic en procesar!");
        } catch (IOException ex) {
            Logger.getLogger(RadicacionBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getEstado(Integer id) throws Exception {
        return radicacionBO.getListEstadosArchivoRadicado(id).getNombre();
    }

    public void newAction(ActionEvent event) {
        inputStream = null;
        gruposArchivosRadicadosId = null;
        tiposArchivoRadicadoId = null;
        fileName = null;
        finished = false;
        haveHeader = false;
        allowTypes = "-1";
        listLog = new LinkedList<>();
        today = new Date();
        listArchivosRadicados = new LinkedList<>();
    }

    public void generateAction(ActionEvent event) {
        try {
            camposArchivoRadicadoList = radicacionBO.getListCamposArchivoRadicado(tiposArchivoRadicadoId);
            if (camposArchivoRadicadoList.isEmpty()) {
                JSFUtil.addError("No existe una configuración de campos para este tipo de archivo");
            } else if (inputStream != null) {
                saveTexto();
            } else {
                JSFUtil.addError("Debe seleccionar un archivo");
            }
        } catch (Exception ex) {
            Logger.getLogger(RadicacionBacking.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void saveTexto() {
        ArchivosRadicados archivosRadicados = new ArchivosRadicados();
        try {
            archivosRadicados.setIdTipoArchivoRadicado(tiposArchivoRadicadoId);
            archivosRadicados.setIdEstadoArchivoRadicado(1);
            archivosRadicados.setNombreArchivo(fileName);
            archivosRadicados.setNumeroEntidadesCargadas(0);
            archivosRadicados.setNumeroRegistrosCargados(0);
            archivosRadicados.setCodigoHash("0");
            archivosRadicados.setFechaRadicacion(new Date());
            archivosRadicados.setUsuarioRadicacion(JSFUtil.getCurrentUser().getUsername());
            radicacionBO.save(archivosRadicados);
            int numFields = camposArchivoRadicadoList.size() + 1;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                int row = 0;
                listLog = new LinkedList<>();
                listTexto = new LinkedList<>();
                while ((line = reader.readLine()) != null) {
                    if (!haveHeader || row > 0) {
                        String[] fields = line.split(tiposArchivoRadicado.getSeparadorCampo());
                        if (numFields != fields.length) {
                            saveLog(archivosRadicados.getId(), row + 1,
                                    "E", "ERR_002",
                                    "El número de campos del archivo es difrente al esperado (Esperado: " + numFields
                                    + ", Actual: " + fields.length + ") ");
                        } else {
                            int index = 0;
                            boolean validate = true;
                            OrdenesVentasSecundariaTexto object = new OrdenesVentasSecundariaTexto();
                            object.setIdArchivoRadicado(archivosRadicados.getId());
                            int count = 0;
                            for (CamposArchivoRadicado camposArchivoRadicado : camposArchivoRadicadoList) {
                                String cell = fields[index];
                                Object value = new Object();
                                if (!cell.trim().equals("*")) {
                                    if (!camposArchivoRadicado.getIsNullable() && (cell.isEmpty() || cell.trim().length() == 0)) {
                                        saveLog(archivosRadicados.getId(), row + 1, "E", "ERR_003", "El campo "
                                                + camposArchivoRadicado.getColumnName() + " es obligatorio");
                                    } else {
                                        System.out.println("Campo: " + camposArchivoRadicado.getColumnName()
                                                + " Valor: " + cell + " Longitud: " + cell.length());
                                        Integer characterMaximumLength = camposArchivoRadicado.getCharacterMaximumLength() == null ? MAX_VARCHAR
                                                : camposArchivoRadicado.getCharacterMaximumLength();
                                        switch (camposArchivoRadicado.getDataType().toLowerCase()) {
                                            case "int":
                                                try {
                                                    value = new Integer(cell);
                                                    Integer precision
                                                            = camposArchivoRadicado.getNumericPrecision() == null ? MAX_INTEGER
                                                                    : camposArchivoRadicado.getNumericPrecision();
                                                    if (value.toString().length() > precision) {
                                                        validate = false;
                                                        saveLog(archivosRadicados.getId(), row + 1, "E", "ERR_005",
                                                                "La longitud del campo "
                                                                + camposArchivoRadicado.getColumnName()
                                                                + " es inválida. Se espera máximo "
                                                                + camposArchivoRadicado.getDataType().toUpperCase());
                                                    }
                                                } catch (Exception ex) {
                                                    if (!cell.trim().isEmpty()) {
                                                        validate = false;
                                                        saveLog(archivosRadicados.getId(), row + 1, "E", "ERR_004",
                                                                "El tipo dato del campo "
                                                                + camposArchivoRadicado.getColumnName()
                                                                + " es inválido. Se espera un tipo de dato "
                                                                + camposArchivoRadicado.getDataType().toUpperCase());
                                                    } else {
                                                        value = null;
                                                    }
                                                }
                                                break;
                                            case "varchar":
                                                value = cell;
                                                if (value.toString().length() > characterMaximumLength) {
                                                    validate = false;
                                                    saveLog(archivosRadicados.getId(), row + 1, "E", "ERR_005",
                                                            "La longitud del campo "
                                                            + camposArchivoRadicado.getColumnName()
                                                            + " es inválida. Se espera máximo "
                                                            + camposArchivoRadicado.getDataType().toUpperCase());
                                                }
                                                break;
                                            case "date":
                                                if (value.toString().length() > characterMaximumLength) {
                                                    validate = false;
                                                    saveLog(archivosRadicados.getId(), row + 1, "E", "ERR_005",
                                                            "La longitud del campo "
                                                            + camposArchivoRadicado.getColumnName()
                                                            + " es inválida. Se espera máximo "
                                                            + camposArchivoRadicado.getDataType().toUpperCase());
                                                } else {
                                                    String dateFormat = "yyyy-MM-dd";
                                                    boolean validateDate = JSFUtil.isThisDateValid(cell, dateFormat);
                                                    if (!validateDate && !cell.trim().isEmpty()) {
                                                        validate = false;
                                                        saveLog(archivosRadicados.getId(), row + 1, "E", "ERR_006",
                                                                "El formato del campo "
                                                                + camposArchivoRadicado.getColumnName()
                                                                + " es inválido. El formato esperado es " + dateFormat);
                                                    } else {
                                                        value = cell;
                                                    }
                                                }
                                                break;
                                            case "time":
                                                if (value.toString().length() > characterMaximumLength) {
                                                    validate = false;
                                                    saveLog(archivosRadicados.getId(), row + 1, "E", "ERR_005",
                                                            "La longitud del campo "
                                                            + camposArchivoRadicado.getColumnName()
                                                            + " es inválida. Se espera máximo "
                                                            + camposArchivoRadicado.getDataType().toUpperCase());
                                                } else {
                                                    String timeFormat = "yyyy-MM-dd HH:mm";
                                                    boolean validateTime = JSFUtil.isThisDateValid("1900-01-01 " + cell, timeFormat);
                                                    if (!validateTime && !cell.trim().isEmpty()) {
                                                        validate = false;
                                                        saveLog(archivosRadicados.getId(), row + 1, "E", "ERR_006",
                                                                "El formato del campo "
                                                                + camposArchivoRadicado.getColumnName()
                                                                + " es inválido. El formato esperado es " + timeFormat);
                                                    } else {
                                                        value = cell;
                                                    }
                                                }
                                                break;
                                            case "bit":
                                                Boolean validateBoolean = cell.equals("1") || cell.equals("0");
                                                if (!validateBoolean) {
                                                    validate = false;
                                                    saveLog(archivosRadicados.getId(), row + 1, "E", "ERR_004",
                                                            "El tipo dato del campo "
                                                            + camposArchivoRadicado.getColumnName()
                                                            + " es inválido. Se espera un tipo de dato "
                                                            + camposArchivoRadicado.getDataType().toUpperCase());
                                                } else {
                                                    value = cell;
                                                }
                                                break;
                                        }
                                    }
                                    index++;
                                    setValue(object, camposArchivoRadicado.getColumnName(), value);
                                } else {
                                    count++;
                                }
                            }

                            if (count + 1 == numFields) {
                                validate = false;
                                saveLog(archivosRadicados.getId(), row + 1, "E", "ERR_007", "Fila en blanco");
                            }

                            if (validate) {
                                object.setRow(row + 1);
                                listTexto.add(object);
                            }
                        }
                    }
                    row++;
                }

                if ((row == 0) || (row == 1 && haveHeader)) {
                    saveLog(archivosRadicados.getId(), row, "E", "ERR_001",
                            "El archivo está vacío");
                }

                if ((listTexto.size() == row) || (listTexto.size() == row - 1 && haveHeader)) {
                    for (OrdenesVentasSecundariaTexto texto : listTexto) {
                        radicacionBO.save(texto);
                    }
                    archivosRadicados.setIdEstadoArchivoRadicado(3);
                    archivosRadicados.setFechaActualizacion(new Date());
                    archivosRadicados.setUsuarioActualizacion(JSFUtil.getCurrentUser().getUsername());
                    radicacionBO.save(archivosRadicados);

                    saveFinal();

                    archivosRadicados.setIdEstadoArchivoRadicado(5);
                    archivosRadicados.setFechaActualizacion(new Date());
                    archivosRadicados.setUsuarioActualizacion(JSFUtil.getCurrentUser().getUsername());
                    radicacionBO.save(archivosRadicados);
                } else {
                    archivosRadicados.setIdEstadoArchivoRadicado(2);
                    archivosRadicados.setFechaActualizacion(new Date());
                    archivosRadicados.setUsuarioActualizacion(JSFUtil.getCurrentUser().getUsername());
                    radicacionBO.save(archivosRadicados);
                }

                if (listLog.isEmpty()) {
                    OrdenesVentasSecundariaLog log = new OrdenesVentasSecundariaLog();
                    log.setCodigoTipoMensaje("E");
                    log.setCodigoMensaje("ERR_000");
                    log.setMensaje("Sin Errores");
                    listLog.add(log);
                }
            } catch (IOException ex) {
                Logger.getLogger(RadicacionBacking.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    inputStream.close();
                    JSFUtil.addInfo("Proceso terminado. Revisar el log.");
                    finished = true;
                } catch (IOException ex) {
                    Logger.getLogger(RadicacionBacking.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception ex) {
            saveLog(archivosRadicados.getId(), 0, "E", "ERR_999", "La tabla de TEXTO no corresponde con la configuración del tipo de archivo");
            try {
                archivosRadicados.setIdEstadoArchivoRadicado(2);
                archivosRadicados.setFechaActualizacion(new Date());
                archivosRadicados.setUsuarioActualizacion(JSFUtil.getCurrentUser().getUsername());
                radicacionBO.save(archivosRadicados);
                Logger.getLogger(RadicacionBacking.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex1) {
                Logger.getLogger(RadicacionBacking.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    private void saveFinal() {
        listTexto.stream().forEach((_item) -> {
            try {
                OrdenesVentasSecundaria ordenesVentasSecundaria = new OrdenesVentasSecundaria();
                ordenesVentasSecundaria.setIdArchivoRadicado(_item.getIdArchivoRadicado());
                ordenesVentasSecundaria.setCodigoCliente(_item.getCodigoCliente());
                ordenesVentasSecundaria.setNumeroIdentificacionDestinatario(_item.getNumeroIdentificacionDestinatario());
                ordenesVentasSecundaria.setBillToNombre(_item.getBillToNombre());
                ordenesVentasSecundaria.setBillToTelefonos(_item.getBillToTelefonos());
                ordenesVentasSecundaria.setBillToEmail(_item.getBillToEmail());
                ordenesVentasSecundaria.setCodigoCiudad(_item.getCodigoCiudad());
                ordenesVentasSecundaria.setDireccion(_item.getDireccion());
                ordenesVentasSecundaria.setIndicacionesDireccion(_item.getIndicacionesDireccion());
                ordenesVentasSecundaria.setShipToNombre(_item.getShipToNombre());
                ordenesVentasSecundaria.setShipToTelefonos(_item.getShipToTelefonos());
                ordenesVentasSecundaria.setShipToEmail(_item.getShipToEmail());
                ordenesVentasSecundaria.setNumeroDocumentoCliente(_item.getNumeroDocumentoCliente());
                ordenesVentasSecundaria.setFechaEntregaPreferidaMinima(JSFUtil.stringToDate(_item.getFechaEntregaPreferidaMinima(), "yyyy-MM-dd"));
                ordenesVentasSecundaria.setFechaEntregaPreferidaMaxima(JSFUtil.stringToDate(_item.getFechaEntregaPreferidaMaxima(), "yyyy-MM-dd"));
                ordenesVentasSecundaria.setHoraEntregaPreferidaMinima(JSFUtil.stringToDate("1900-01-01 " + _item.getHoraEntregaPreferidaMinima(), "yyyy-MM-dd HH:mm"));
                ordenesVentasSecundaria.setHoraEntregaPreferidaMaxima(JSFUtil.stringToDate("1900-01-01 " + _item.getHoraEntregaPreferidaMaxima(), "yyyy-MM-dd HH:mm "));
                ordenesVentasSecundaria.setValorDeclarado(_item.getValorDeclarado() == null ? null : new Integer(_item.getValorDeclarado()));
                ordenesVentasSecundaria.setCodigoFormaDePago(_item.getCodigoFormaDePago());
                ordenesVentasSecundaria.setRequiereMaquila(_item.getRequiereMaquila() == null || _item.getRequiereMaquila().trim().isEmpty() ? null : _item.getRequiereMaquila().trim().equals("1"));
                ordenesVentasSecundaria.setNotasOrden(_item.getNotasOrden());
                ordenesVentasSecundaria.setCodigoProducto(_item.getCodigoProducto());
                ordenesVentasSecundaria.setCantidad(_item.getCantidad() == null || _item.getCantidad().isEmpty() ? null : new Integer(_item.getCantidad()));
                ordenesVentasSecundaria.setCodigoUnidad(_item.getCodigoUnidad());
                ordenesVentasSecundaria.setNumeroDeLote(_item.getNumeroDeLote());
                ordenesVentasSecundaria.setNotasItemOrden(_item.getNotasItemOrden());
                radicacionBO.save(ordenesVentasSecundaria);
            } catch (Exception ex) {
                saveLog(_item.getIdArchivoRadicado(), _item.getRow(), "C", "ERR_100",
                        ex.getMessage());
                Logger.getLogger(RadicacionBacking.class.getName()).log(Level.SEVERE, null, "Fila: " + _item.getRow() + " - " + ex);
            }
        });
    }

    private void saveLog(Integer idArchivoRadicado, Integer linea, String codigoTipoMensaje,
            String codigoMensaje, String mensaje) {
        try {
            OrdenesVentasSecundariaLog log = new OrdenesVentasSecundariaLog();
            log.setIdArchivoRadicado(idArchivoRadicado);
            log.setCodigoTipoMensaje(codigoTipoMensaje);
            log.setCodigoMensaje(codigoMensaje);
            log.setMensaje(mensaje);
            log.setIdOrdenVentaSecundaria(linea);
            radicacionBO.save(log);
            listLog.add(log);
        } catch (Exception ex) {
            Logger.getLogger(RadicacionBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setValue(Object object, String columnName, Object value) {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            String methodName = "get" + JSFUtil.toUpperCaseFirst(field.getName());
            try {
                Method method = object.getClass().getDeclaredMethod(methodName, new Class[]{});
                Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
                for (Annotation annotation : declaredAnnotations) {
                    try {
                        Column column = (Column) annotation;
                        if (column.name().toLowerCase().equalsIgnoreCase(columnName.toLowerCase())) {
                            if (value != null && value.toString().trim().isEmpty()) {
                                value = null;
                            }
                            BeanUtils.setProperty(object, field.getName(), value);
                        }
                    } catch (IllegalAccessException | InvocationTargetException | ClassCastException ex) {

                    }
                }
            } catch (NoSuchMethodException ex) {

            }
        }
    }

    private void setValueFinal(Object object, String columnName, Object value) {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            String methodName = "get" + JSFUtil.toUpperCaseFirst(field.getName());
            try {
                Method method = object.getClass().getDeclaredMethod(methodName, new Class[]{});
                Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
                for (Annotation annotation : declaredAnnotations) {
                    try {
                        Column column = (Column) annotation;
                        if (column.name().toLowerCase().equalsIgnoreCase(columnName.toLowerCase())) {
                            if (value != null && value.toString().isEmpty()) {
                                value = null;
                            }
                            BeanUtils.setProperty(object, field.getName(), value);
                        }
                    } catch (IllegalAccessException | InvocationTargetException | ClassCastException ex) {

                    }
                }
            } catch (NoSuchMethodException ex) {

            }
        }
    }

    public void searchAction(ActionEvent event) {
        try {
            String fechaInicialString = JSFUtil.formatDate(fechaInicial, "yyyy/MM/dd") + " 00:00:00";
            String fechaFinalString = JSFUtil.formatDate(fechaFinal, "yyyy/MM/dd") + " 23:59:59";
            listArchivosRadicados
                    = radicacionBO.getListArchivosRadicados(idEstado, fechaInicialString, fechaFinalString);
            modelArchivosRadicados = new DataModelCustom<>(listArchivosRadicados);
        } catch (Exception ex) {
            Logger.getLogger(RadicacionBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void searchLOGAction() {
        try {
            List<OrdenesVentasSecundariaLog> list
                    = radicacionBO.getListOrdenesVentasSecundariaLog(selectedArchivosRadicados.getId());
            modelLog = new DataModelCustom<>(list);
        } catch (Exception ex) {
            Logger.getLogger(RadicacionBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
