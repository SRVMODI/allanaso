package com.astix.allanasosfa;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.astix.Common.CommonInfo;
import com.allanasosfa.truetime.TimeUtils;
import com.astix.allanasosfa.database.AppDataSource;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class ServiceWorker
{
	SharedPreferences sharedPref;

	public static int flagExecutedServiceSuccesfully=0;
	public int chkTblStoreListContainsRow=1;

	//Live Path WebServiceAndroidParagSFATesting
	//public String UrlForWebService="http://115.124.126.184/WebServiceAndroidParagSFA/Service.asmx";

	//Testing Path
	//public String UrlForWebService="http://115.124.126.184/WebServiceAndroidParagSFATesting/Service.asmx";
	public String UrlForWebService= CommonInfo.WebServicePath.trim();
	//int counts;
	public String currSysDate;
	public String SysDate;
	public int newStat = 0;
	public int timeout=0;
	Locale locale  = new Locale("en", "UK");
	String pattern = "###.##";
	DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(locale);
	String movie_name;
	String director;
	String exceptionCode;
	private Context context;
	//private ServiceWorker _activity;
	private ContextWrapper cw;


	public ServiceWorker fnGetDistributorTodaysStock(Context ctx,int CustomerNodeID, int CustomerNodeType,String bydate,String IMEINo,String SysDate, int AppVersionID)
	{
		this.context = ctx;


		int chkTblStoreListContainsRow=1;
		StringReader read;
		InputSource inputstream;
		final String SOAP_ACTION = "http://tempuri.org/fnGetDistributorTodaysStock";
		final String METHOD_NAME = "fnGetDistributorTodaysStock";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;

		//Create request
		SoapObject table = null; //Contains table of dataset that returned
		// through SoapObject
		SoapObject client = null; //Its the client petition to the web service
		SoapObject tableRow = null; //Contains row of table
		SoapObject responseBody = null; //Contains XML content of dataset

		//DBHelper dbengine=new DBHelper(ctx);
		AppDataSource dbengine = AppDataSource.getInstance(context);
		//dbengine.open();
		//SoapObject param
		HttpTransportSE transport = null; // That call webservice
		SoapSerializationEnvelope sse = null;
		sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		sse.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,0);

		ServiceWorker setmovie = new ServiceWorker();
		try {
			client = new SoapObject(NAMESPACE, METHOD_NAME);
			//client.addProperty("ApplicationID", ApplicationID);
			//client.addProperty("uuid", uuid.toString());

			//String rID=dbengine.GetActiveRouteIDSunilAgain();


			Date currDate= new Date();
			SimpleDateFormat currDateFormat =new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);

			currSysDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_TIME_FORMAT);
			SysDate = currSysDate.trim().toString();

			client.addProperty("CustomerNodeID",CustomerNodeID);
			client.addProperty("CustomerNodeType",CustomerNodeType);

			client.addProperty("bydate", bydate.toString());
			client.addProperty("IMEINo", IMEINo.toString());
			//client.addProperty("rID", rID.toString());
			client.addProperty("SysDate", SysDate.toString());
			client.addProperty("AppVersionID", dbengine.dbHelper.AppVersionID);


			sse.setOutputSoapObject(client);
			sse.bodyOut = client;
			androidHttpTransport.call(SOAP_ACTION, sse);
			responseBody = (SoapObject)sse.bodyIn;
			// This step: get file XML
			//responseBody = (SoapObject) sse.getResponse();
			int totalCount = responseBody.getPropertyCount();

			// System.out.println("Kajol 2 :"+totalCount);
			String resultString=androidHttpTransport.responseDump;

			String name=responseBody.getProperty(0).toString();

			// System.out.println("Kajol 3 :"+name);

			XMLParser xmlParser = new XMLParser();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(name));
			Document doc = db.parse(is);

			//  dbengine.deleteDistributorStockTbles();

			NodeList tblDistributorDayReportNode = doc.getElementsByTagName("tblDistributorDayReport");
			for (int i = 0; i < tblDistributorDayReportNode.getLength(); i++)
			{
				String ProductNodeID="0";
				String ProductNodeType="0";
				String SKUName="0";
				String FlvShortName="0";
				String StockDate="0";

				Element element = (Element) tblDistributorDayReportNode.item(i);

				if(!element.getElementsByTagName("ProductNodeID").equals(null))
				{
					NodeList ProductNodeIDNode = element.getElementsByTagName("ProductNodeID");
					Element      line = (Element) ProductNodeIDNode.item(0);
					if(ProductNodeIDNode.getLength()>0)
					{
						ProductNodeID=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("ProductNodeType").equals(null))
				{
					NodeList ProductNodeTypeNode = element.getElementsByTagName("ProductNodeType");
					Element      line = (Element) ProductNodeTypeNode.item(0);
					if(ProductNodeTypeNode.getLength()>0)
					{
						ProductNodeType=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("SKUName").equals(null))
				{
					NodeList SKUNameNode = element.getElementsByTagName("SKUName");
					Element      line = (Element) SKUNameNode.item(0);
					if(SKUNameNode.getLength()>0)
					{
						SKUName=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("FlvShortName").equals(null))
				{
					NodeList FlvShortNameNode = element.getElementsByTagName("FlvShortName");
					Element      line = (Element) FlvShortNameNode.item(0);
					if(FlvShortNameNode.getLength()>0)
					{
						FlvShortName=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("StockDate").equals(null))
				{
					NodeList StockDateNode = element.getElementsByTagName("StockDate");
					Element      line = (Element) StockDateNode.item(0);
					if(StockDateNode.getLength()>0)
					{
						StockDate=xmlParser.getCharacterDataFromElement(line);
					}
				}


				//AutoId= i +1;
				//dbengine.savetblDistributorDayReport(ProductNodeID, ProductNodeType, SKUName, FlvShortName,StockDate,CustomerNodeID,CustomerNodeType);

			}


			NodeList tblDistributorDayReportColumnsDescNode = doc.getElementsByTagName("tblDistributorDayReportColumnsDesc");
			for (int i = 0; i < tblDistributorDayReportColumnsDescNode.getLength(); i++)
			{
				String DistDayReportCoumnName="0";
				String DistDayReportColumnDisplayName="0";

				Element element = (Element) tblDistributorDayReportColumnsDescNode.item(i);

				if(!element.getElementsByTagName("DistDayReportCoumnName").equals(null))
				{
					NodeList DistDayReportCoumnNameNode = element.getElementsByTagName("DistDayReportCoumnName");
					Element      line = (Element) DistDayReportCoumnNameNode.item(0);
					if(DistDayReportCoumnNameNode.getLength()>0)
					{
						DistDayReportCoumnName=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("DistDayReportColumnDisplayName").equals(null))
				{
					NodeList DistDayReportColumnDisplayNameNode = element.getElementsByTagName("DistDayReportColumnDisplayName");
					Element      line = (Element) DistDayReportColumnDisplayNameNode.item(0);
					if(DistDayReportColumnDisplayNameNode.getLength()>0)
					{
						DistDayReportColumnDisplayName=xmlParser.getCharacterDataFromElement(line);
					}
				}

				//AutoId= i +1;
				dbengine.savetblDistributorDayReportColumnsDesc(DistDayReportCoumnName, DistDayReportColumnDisplayName,CustomerNodeID,CustomerNodeType);
			}

			//dbengine.close();;
			setmovie.director = "1";

			return setmovie;

		} catch (Exception e)
		{
			//dbengine.close();;
			setmovie.exceptionCode=e.getCause().getMessage();
			System.out.println("Aman Exception occur in fnDistributor :"+e.toString());
			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();


			return setmovie;
		}
	}



	public int fnConfirmStockUpdate(Context ctx)
	{
		this.context = ctx;

		int flgDataConfirmed=-1;
		final String SOAP_ACTION = "http://tempuri.org/fnGetPDAConfirmVanStock";
		final String METHOD_NAME = "fnGetPDAConfirmVanStock";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;

		// through SoapObject
		SoapObject client = null; //Its the client petition to the web service
		SoapObject responseBody = null; //Contains XML content of dataset

		AppDataSource dbengine = AppDataSource.getInstance(context);
		int vanCycleId= dbengine.fetchtblVanCycleId();
		String CycStartTime=dbengine.fetchtblVanCycleTime();
		//dbengine.open();

		//SoapObject param
		SoapSerializationEnvelope sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		sse.dotNet = true;

		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,0);

		ServiceWorker setmovie = new ServiceWorker();

		try
		{
			client = new SoapObject(NAMESPACE, METHOD_NAME);

			client.addProperty("CycleID", vanCycleId);
			client.addProperty("CycleDate", CycStartTime);

			sse.setOutputSoapObject(client);

			sse.bodyOut = client;

			androidHttpTransport.call(SOAP_ACTION, sse);

			responseBody = (SoapObject)sse.bodyIn;

			int totalCount = responseBody.getPropertyCount();

			String resultString=androidHttpTransport.responseDump;

			String name=responseBody.getProperty(0).toString();

			XMLParser xmlParser = new XMLParser();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(name));
			Document doc = db.parse(is);

			//dbengine.deleteIncentivesTbles();


			NodeList tblflgDataConfirmedNode = doc.getElementsByTagName("tblflgDataConfirmed");
			for (int i = 0; i < tblflgDataConfirmedNode.getLength(); i++)
			{




				Element element = (Element) tblflgDataConfirmedNode.item(i);

				if(!element.getElementsByTagName("flgDataConfirmed").equals(null))
				{
					NodeList flgStockTransNode = element.getElementsByTagName("flgDataConfirmed");
					Element      line = (Element) flgStockTransNode.item(0);
					if(flgStockTransNode.getLength()>0)
					{
						flgDataConfirmed=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));

					}
				}




			}


			//dbengine.close();;
			setmovie.director = "1";

			return flgDataConfirmed;

		}
		catch (Exception e)
		{
			setmovie.exceptionCode=e.getCause().getMessage();
			//dbengine.close();;
			System.out.println("Aman Exception occur in fnIncentive :"+e.toString());

			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();

			return flgDataConfirmed;
		}
	}



	public ServiceWorker getConfirmtionRqstStock(Context ctx,String RqstdStk,String uuid,String CoverageAreaNodeID,String coverageAreaNodeType,int statusID)
	{
		this.context = ctx;

		decimalFormat.applyPattern(pattern);

		int chkTblStoreListContainsRow=1;
		StringReader read;
		InputSource inputstream;
		final String SOAP_ACTION = "http://tempuri.org/fnSpVanStockRequestSave";
		final String METHOD_NAME = "fnSpVanStockRequestSave";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;
		//Create request
		SoapObject table = null; // Contains table of dataset that returned
		// through SoapObject
		SoapObject client = null; // Its the client petition to the web service
		SoapObject tableRow = null; // Contains row of table
		SoapObject responseBody = null; // Contains XML content of dataset

		//SoapObject param
		HttpTransportSE transport = null; // That call webservice
		SoapSerializationEnvelope sse = null;

		sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		sse.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,timeout);

		ServiceWorker setmovie = new ServiceWorker();
		Date currDate= new Date();
		SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);

		currSysDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
		String crntDate = currSysDate.trim().toString();
		try {
			client = new SoapObject(NAMESPACE, METHOD_NAME);


			client.addProperty("bydate", crntDate);
			client.addProperty("IMEINo", uuid.toString());
			client.addProperty("CoverageAreaNodeID", CoverageAreaNodeID);

			client.addProperty("coverageAreaNodeType", coverageAreaNodeType);
			client.addProperty("Prdvalues", RqstdStk);
			client.addProperty("StatusID", statusID);


			sse.setOutputSoapObject(client);

			sse.bodyOut = client;

			androidHttpTransport.call(SOAP_ACTION, sse);

			responseBody = (SoapObject)sse.bodyIn;
			// This step: get file XML
			//responseBody = (SoapObject) sse.getResponse();
			int totalCount = responseBody.getPropertyCount();

			// // System.out.println("Kajol 2 :"+totalCount);
			String resultString=androidHttpTransport.responseDump;

			String name=responseBody.getProperty(0).toString();

			XMLParser xmlParser = new XMLParser();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(name));
			Document doc = db.parse(is);

			//dbengine.open();




			NodeList tblDistributorListForSONode = doc.getElementsByTagName("tblflgRequestStockAccepted");
			for (int i = 0; i < tblDistributorListForSONode.getLength(); i++)
			{

				String VanLoadUnLoadCycID="0";
				String flgRequestAccept="0";


				Element element = (Element) tblDistributorListForSONode.item(i);
				if(!element.getElementsByTagName("VanLoadUnLoadCycID").equals(null))
				{
					NodeList VanLoadUnLoadCycIDNode = element.getElementsByTagName("VanLoadUnLoadCycID");
					Element     line = (Element) VanLoadUnLoadCycIDNode.item(0);
					if (VanLoadUnLoadCycIDNode.getLength()>0)
					{
						VanLoadUnLoadCycID=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("flgRequestAccept").equals(null))
				{
					NodeList flgRequestAcceptNode = element.getElementsByTagName("flgRequestAccept");
					Element     line = (Element) flgRequestAcceptNode.item(0);
					if (flgRequestAcceptNode.getLength()>0)
					{
						flgRequestAccept=xmlParser.getCharacterDataFromElement(line);

						if(flgRequestAccept.equals("1"))
						{
							setmovie.director = "1";
							break;
						}


						else
						{
							setmovie.director = "0";
							break;
						}


					}
				}

			}




			//dbengine.close();
			return setmovie;

		} catch (Exception e) {
			//setmovie.exceptionCode=e.getCause().getMessage();
			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();
			//dbengine.close();

			return setmovie;
		}
	}

	public ServiceWorker submitDayEndClosure(Context ctx,String uuid,int flgUnloading)
	{
		this.context = ctx;
		AppDataSource dbengine = AppDataSource.getInstance(context);

		decimalFormat.applyPattern(pattern);

		int chkTblStoreListContainsRow=1;
		StringReader read;
		InputSource inputstream;
		final String SOAP_ACTION = "http://tempuri.org/fnPDAVanDayEndDet";
		final String METHOD_NAME = "fnPDAVanDayEndDet";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;
		//Create request
		SoapObject table = null; // Contains table of dataset that returned
		// through SoapObject
		SoapObject client = null; // Its the client petition to the web service
		SoapObject tableRow = null; // Contains row of table
		SoapObject responseBody = null; // Contains XML content of dataset

		//SoapObject param
		HttpTransportSE transport = null; // That call webservice
		SoapSerializationEnvelope sse = null;

		sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		sse.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,timeout);

		ServiceWorker setmovie = new ServiceWorker();
		Date currDate= new Date();

		long syncTIMESTAMP = System.currentTimeMillis();
		Date dateobj = new Date(syncTIMESTAMP);
		SimpleDateFormat df = new SimpleDateFormat(
				"dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
		String EndTS = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_TIME_FORMAT);

		int cycleId=dbengine.fetchtblVanCycleId();
		if(cycleId==-1)
		{
			cycleId=0;

		}

		SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		String rID=dbengine.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
		currSysDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
		String crntDate = currSysDate.trim().toString();
		try {
			client = new SoapObject(NAMESPACE, METHOD_NAME);



			client.addProperty("PDA_IMEI", uuid.toString());
			client.addProperty("DayEndTime", EndTS);
			client.addProperty("VisitDate", crntDate);

			client.addProperty("AppVersionID", CommonInfo.DATABASE_VERSIONID);
			client.addProperty("VanLoadUnLoadCycID", cycleId);
			client.addProperty("flgUnloading", flgUnloading);


			sse.setOutputSoapObject(client);

			sse.bodyOut = client;

			androidHttpTransport.call(SOAP_ACTION, sse);

			responseBody = (SoapObject)sse.bodyIn;
			// This step: get file XML
			//responseBody = (SoapObject) sse.getResponse();
			int totalCount = responseBody.getPropertyCount();

			// // System.out.println("Kajol 2 :"+totalCount);
			String resultString=androidHttpTransport.responseDump;

			String name=responseBody.getProperty(0).toString();

			XMLParser xmlParser = new XMLParser();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(name));
			Document doc = db.parse(is);

			//dbengine.open();




			NodeList tblDistributorListForSONode = doc.getElementsByTagName("tblflgPDAVanDayEndDet");
			for (int i = 0; i < tblDistributorListForSONode.getLength(); i++)
			{

				String VanLoadUnLoadCycID="0";
				String flgRequestAccept="0";


				Element element = (Element) tblDistributorListForSONode.item(i);

				if(!element.getElementsByTagName("flgDayEndRequestAccept").equals(null))
				{
					NodeList flgRequestAcceptNode = element.getElementsByTagName("flgDayEndRequestAccept");
					Element     line = (Element) flgRequestAcceptNode.item(0);
					if (flgRequestAcceptNode.getLength()>0)
					{
						flgRequestAccept=xmlParser.getCharacterDataFromElement(line);
						if(flgRequestAccept.equals("1"))
						{
							setmovie.director = "1";
							break;
						}
						else
						{
							setmovie.director = "0";
							break;
						}
					}
				}

			}




			//dbengine.close();
			return setmovie;

		} catch (Exception e) {
			//setmovie.exceptionCode=e.getCause().getMessage();
			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();
			//dbengine.close();

			return setmovie;
		}
	}

}

