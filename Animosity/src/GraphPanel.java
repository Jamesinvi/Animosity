import java.awt.*;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class GraphPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8297203371989662047L;
	int step=0;
	AnimosityFrame frm;
	
	//Creating Charts
	JFreeChart XYChart;
	JFreeChart areaChart;
	//Creating Series And Collections
	XYSeries xyPopulationToT;
	XYSeries xyForce;
	XYSeries xySpeciesOne;
	XYSeries xySpeciesTwo;
	XYSeriesCollection xyDataset;
	XYSeriesCollection xyAreaDataset;
	XYSeries xySpeciesThree;

	//Constructor
	public GraphPanel(AnimosityFrame f,FlowLayout flowLayout){
		this.frm=f;
		XYChart=ChartFactory.createXYLineChart("TotalPopulation", "Time", "Population",tempDataset());
		areaChart=ChartFactory.createXYAreaChart("Population", "Time", "Value", createXYAreaDataset()
												,PlotOrientation.VERTICAL,true,true,false);
	  	ChartPanel XYChartPanel=new ChartPanel(XYChart);
		ChartPanel areaChartPanel=new ChartPanel(areaChart);
		this.setLayout(flowLayout);
		this.setBackground(Color.LIGHT_GRAY);
		this.add(XYChartPanel);
		this.add(areaChartPanel);
		XYPlot plot1=areaChart.getXYPlot();
		plot1.setForegroundAlpha(0.5f);
	}

	//CREATE EMPTY XYAreaDataset
	private XYSeriesCollection createXYAreaDataset() {
		xySpeciesOne= new XYSeries("Species 1");
		xySpeciesTwo= new XYSeries("Species 2");
		xySpeciesThree=new XYSeries("Species 3");
		xyAreaDataset=new XYSeriesCollection(xySpeciesOne);
		xyAreaDataset.addSeries(xySpeciesTwo);
		xyAreaDataset.addSeries(xySpeciesThree);
		return xyAreaDataset;
	}
	//CREATE EMPTY XYDataset
	private XYDataset tempDataset(){
		xyPopulationToT=new XYSeries("TotalPop");
		xyForce=new XYSeries("Force");
		xyDataset=new XYSeriesCollection(xyPopulationToT);
		xyDataset.addSeries(xyForce);
		return xyDataset;
	}
	//UPDATE METHODS CALLED BY THE SIMULATION TO ADD VALUES TO CHARTS
	public void updateXYDataset(float value){
		step++;
		xyPopulationToT.add(step, value);
	}
	public void updateXYAreasDataset(float value1,float value2,float value3){
		xySpeciesOne.add(step,value1);
		xySpeciesTwo.add(step,value2);
		xySpeciesThree.add(step,value3);
	}

}
