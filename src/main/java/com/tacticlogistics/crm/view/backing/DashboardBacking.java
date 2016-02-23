package com.tacticlogistics.crm.view.backing;

import com.tacticlogistics.crm.model.bo.DashboardBO;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.context.annotation.Scope;

import com.tacticlogistics.crm.view.util.DataModelCustom;
import com.tacticlogistics.crm.model.entities.dashboard.Resultados;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

@Named
@Scope("view")
public class DashboardBacking extends BaseBacking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private DashboardBO dashboardBO;

    private DataModelCustom<Resultados> resultadosModel;
    private Resultados resultado;

    private List<Resultados> listBar;

    private Resultados kpiWholesalerRejections;
    private Resultados kpiCapacityOccupation;
    private Resultados kpiStockDaysTACTIC;
    private Resultados kpiOTD;
    private Resultados kpiOTIF;

    private BarChartModel barModel;

    private Integer annio;
    private Integer mes;

    private Double min = 0.0;
    private Double max = 0.0;

    public DashboardBacking() {
        annio = 2015;
        mes = 6;
    }

    @PostConstruct
    public void init() {
        refreshAction();
    }

    public DataModelCustom<Resultados> getResultadosModel() {
        return resultadosModel;
    }

    public void setResultadosModel(DataModelCustom<Resultados> resultadosModel) {
        this.resultadosModel = resultadosModel;
    }

    public Resultados getResultado() {
        return resultado;
    }

    public void setResultado(Resultados resultado) {
        this.resultado = resultado;
    }

    public Resultados getKpiWholesalerRejections() {
        return kpiWholesalerRejections;
    }

    public void setKpiWholesalerRejections(Resultados kpiWholesalerRejections) {
        this.kpiWholesalerRejections = kpiWholesalerRejections;
    }

    public Resultados getKpiCapacityOccupation() {
        return kpiCapacityOccupation;
    }

    public void setKpiCapacityOccupation(Resultados kpiCapacityOccupation) {
        this.kpiCapacityOccupation = kpiCapacityOccupation;
    }

    public Resultados getKpiStockDaysTACTIC() {
        return kpiStockDaysTACTIC;
    }

    public void setKpiStockDaysTACTIC(Resultados kpiStockDaysTACTIC) {
        this.kpiStockDaysTACTIC = kpiStockDaysTACTIC;
    }

    public Resultados getKpiOTD() {
        return kpiOTD;
    }

    public void setKpiOTD(Resultados kpiOTD) {
        this.kpiOTD = kpiOTD;
    }

    public Resultados getKpiOTIF() {
        return kpiOTIF;
    }

    public void setKpiOTIF(Resultados kpiOTIF) {
        this.kpiOTIF = kpiOTIF;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
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

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries real = new ChartSeries();
        real.setLabel("Real");

        for (Resultados row : listBar) {
            real.set(row.getAnnio() + "-" + row.getMes(), row.getReal() == null ? 0 : row.getReal().doubleValue());
            if (row.getReal().doubleValue() < min) {
                min = row.getReal().doubleValue();
            }
            if (row.getReal().doubleValue() > max) {
                max = row.getReal().doubleValue();
            }
        }

        ChartSeries plan = new ChartSeries();
        plan.setLabel("Planeado");

        for (Resultados row : listBar) {
            plan.set(row.getAnnio() + "-" + row.getMes(),
                    row.getPlaneado() == null ? 0 : row.getPlaneado().doubleValue());
            if (row.getReal().doubleValue() < min) {
                min = row.getReal().doubleValue();
            }
            if (row.getReal().doubleValue() > max) {
                max = row.getReal().doubleValue();
            }
        }

        model.addSeries(real);
        model.addSeries(plan);

        return model;
    }

    private void createBarModel() {
        barModel = initBarModel();
        barModel.setShowDatatip(false);
        barModel.setShowPointLabels(true);

        // barModel.setTitle("Bar Chart");
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Valores en " + (kpiOTIF.getUnidad() == null ? '%' : kpiOTIF.getUnidad()));

        Axis yAxis = barModel.getAxis(AxisType.Y);
        // yAxis.setLabel("Births");
        yAxis.setMin(min);
        yAxis.setMax(max * 1.5);
    }

    public void refreshAction() {
        try {
            List<Resultados> list = dashboardBO.getListResultado(annio, mes);
            listBar = dashboardBO.getListResultado("OTIF", annio, mes);
            resultadosModel = new DataModelCustom<>(list);
            kpiWholesalerRejections = dashboardBO.getResultadoPorKPI("Wholesaler Rejections", annio, mes);
            kpiCapacityOccupation = dashboardBO.getResultadoPorKPI("Capacity Occupation", annio, mes);
            kpiStockDaysTACTIC = dashboardBO.getResultadoPorKPI("Stock Days TACTIC", annio, mes);
            kpiOTD = dashboardBO.getResultadoPorKPI("OTD", annio, mes);
            kpiOTIF = dashboardBO.getResultadoPorKPI("OTIF", annio, mes);
            createBarModel();
        } catch (Exception ex) {
            Logger.getLogger(DashboardBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
