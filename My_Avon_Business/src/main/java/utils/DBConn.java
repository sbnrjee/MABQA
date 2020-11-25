package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import wrappers.AvonWrapper;
import wrappers.Wrappers;

public class DBConn {

	String JDBC_DRIVER;
	String DB_URL;
	String USER;
	String PASS;
	Connection conn=null;
	Statement stmt=null;
	ResultSet rs=null;
	
	String market;
	String marketid;

	public DBConn(String market){
		this.market=market;
	}
	public  void setDBDetails(){

		switch(market){
		case "poland":
			this.JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
			this.DB_URL ="jdbc:oracle:thin:@ryelxwebeqfdbc1.rye.avon.com:1521:webgplqf";
			this.USER = "WEBEEPL_OUSR";
			this.PASS = "WEBEEPL_OUSR";
			marketid="30";
			break;


		case "hungary":
			this.JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
			this.DB_URL ="jdbc:oracle:thin:@ryelxwebeqfdbc1.rye.avon.com:1521:webgceqf";
			this.USER = "WEBEEHU_OUSR";
			this.PASS = "WEBEEHU_OUSR";
			marketid="2";
			break;
		case "czech":
			this.JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
			this.DB_URL ="jdbc:oracle:thin:@ryelxwebeqfdbc1.rye.avon.com:1521:webgceqf";
			this.USER = "WEBEEHU_OUSR";
			this.PASS = "WEBEEHU_OUSR";;
			marketid="24";
			break;
		case "romania":
			this.JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
			this.DB_URL ="jdbc:oracle:thin:@ryelxwebeqfdbc1.rye.avon.com:1521:webgceqf";
			this.USER = "WEBEEHU_OUSR";
			this.PASS = "WEBEEHU_OUSR";
			marketid="27";
			break;
		case "bulgaria":
			this.JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
			this.DB_URL ="jdbc:oracle:thin:@ryelxwebeqfdbc1.rye.avon.com:1521:webgceqf";
			this.USER = "WEBEEHU_OUSR";
			this.PASS = "WEBEEHU_OUSR";
			marketid="31";
			break;

		case "egypt":
			this.JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
			this.DB_URL ="jdbc:oracle:thin:@ryelxwebeqfdbc1.rye.avon.com:1521:webeplqf";
			this.USER = "WEBEEPL_OUSR";
			this.PASS = "WEBEEPL_OUSR";
			marketid="99";
			break;
		case "kazakhstan":
			this.JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
			this.DB_URL ="jdbc:oracle:thin:@ryelxwebeqfdbc1.rye.avon.com:1521:webgceqf";
			this.USER = "WEBEEUA_OUSR";
			this.PASS = "WEBEEUA_OUSR";
			marketid="43";
			break;
		case "kyrgyzstan":
			this.JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
			this.DB_URL ="jdbc:oracle:thin:@ryelxwebeqfdbc1.rye.avon.com:1521:webgceqf";
			this.USER = "WEBEEHU_OUSR";
			this.PASS = "WEBEEHU_OUSR";
			marketid="98";
			break;
		}
	}

	public  void connectToDB() throws ClassNotFoundException, SQLException{
		setDBDetails();
		//STEP 2: Register JDBC driver
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//STEP 3: Open a connection
		System.out.println("Connecting to database..."+ market);
		conn = DriverManager.getConnection(DB_URL,USER,PASS);
		conn.setAutoCommit(false);
		System.out.println("connected successfully");
	}

	public ResultSet runQuery(String sqlQuery) throws SQLException{
		//STEP 4: Execute a query
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sqlQuery);
		System.out.println(rs);
		return rs;
	}



	public void closeConnection() throws SQLException{
		conn.close();
		System.out.println("Connection Closed");
	}



	//commonly needed data from DB in functions


	public void display10AccountNumber(ReadExcel readExcel) throws SQLException, ClassNotFoundException{
		connectToDB();
		//STEP 5: Extract data from result set
		String query=readExcel.getValue("display10AccountNumber");
		//ResultSet rs=runQuery("SELECT acct_nr FROM REP where mrkt_id=" + marketid + " and acct_typ='REP' and rownum<=10");
		ResultSet rs=runQuery(query);
		while(rs.next()){
			int id  = rs.getInt("acct_nr");
			System.out.println("acct_nr: " + id);
		}
		stmt.close();
		rs.close();
		closeConnection();
	}
	public List<String> getnames(String userName) {
	       List<String> names=new ArrayList<String>();
	        try {
	              connectToDB();

	              ResultSet rs=runQuery("select frst_nm,last_nm from rep where acct_nr="+userName+" and mrkt_id="+marketid);

	              while(rs.next()){
	                   names.add(rs.getString(1));
	                   names.add(rs.getString(2));
	              }
	              rs.close();

	              closeConnection();
	        }catch(Exception e){
	               System.out.println(e.getMessage());
	                   
	        }
	        return names;


	  }


    public String getSingleValidProduct(String marketID,String userName,String campaigYrNo,String campaigNo) {
          /* String campaigYrNo=getDbDetails("select CMPGN_YR_NR from slng_day_mlpln where ONLN_ORD_SBMSN_DT>sysdate and mrkt_id='"+marketID+"'  and fld_lvl_cd in (select CREATN_LVL_CD from rep where acct_nr='"+userName+"') and rownum=1 order by CMPGN_NR,CMPGN_DAY_NR ");
           String campaigNo=getDbDetails("select CMPGN_NR from slng_day_mlpln where ONLN_ORD_SBMSN_DT>sysdate and mrkt_id='"+marketID+"'  and fld_lvl_cd in (select CREATN_LVL_CD from rep where acct_nr='"+userName+"') and rownum=1 order by CMPGN_NR,CMPGN_DAY_NR ");
           */
           System.out.println(campaigYrNo);
           System.out.println(campaigNo);
           String product=getDbDetails("select LINE_NR from lcl_line_nr where prod_typ='REG' and for_sale_ind='Y' and mrkt_id='"+marketID+"' and CMPGN_YR_NR ='"+campaigYrNo+"'and CMPGN_NR='"+campaigNo+"'AND LMTD_QTY_OFFR_IND='N' AND REP_IND='Y' AND COORDTR_IND='Y' AND MGR_IND='Y' AND ITEM_FSC_NR IN (select ITEM_FSC_NR from DSTRBTN_CNTR_SBSTTN_PAI where PAI_DMND_DT=trunc(sysdate)AND  AVLBLTY_CD='GRN' AND MRKT_ID='"+marketID+"' ) AND ROWNUM=1");
           System.out.println(product);
           return product;
    }
    
    	
	public int getCurrentCampaignNumber(String accountNumber) throws ClassNotFoundException, SQLException{
		connectToDB();
		int cmpgnNr = 0;
		//System.out.println(marketid);
		//System.out.println(accountNumber);
		//String query="SELECT DISTINCT MIN(S.CMPGN_NR) FROM rep r,rep_addr ra, user_id u, slng_day_mlpln s WHERE u.mrkt_id =" + marketid + " AND u.mrkt_id = r.mrkt_id AND u.acct_nr = r.acct_nr and r.mrkt_id = ra.mrkt_id and r.acct_nr = ra.acct_nr AND r.acct_nr=" + AccountNumber + " AND r.mrkt_id = s.mrkt_id AND r.creatn_lvl_cd = s.fld_lvl_cd AND r.creatn_lvl_nr = s.fld_lvl_nr AND (s.ONLN_ORD_SBMSN_DT)>=SYSDATE";
		//String query=readExcel.getValue("getCurrentCampaignNumber");
		//query=query.replace("?", accountNumber);
		
		//ResultSet rs=runQuery(query);
/*
		while(rs.next()){
			cmpgnNr  = rs.getInt("MIN(S.CMPGN_NR)");
			System.out.println("cmpgn_nr: " + cmpgnNr);
		}
*/	
		
		stmt.close();
		rs.close();
		closeConnection();
		return cmpgnNr;
	}
	public String getNumberOfUpcomingdueTransaction(String userName){

		String result="";
		try{
			connectToDB();
			ResultSet rs=runQuery("select count(*) from rep_tran where tran_due_dt>=sysdate and tran_due_dt <=sysdate+5and tran_due_dt is not null and mrkt_id="+marketid+"and acct_nr="+userName+"  order by TRAN_DT");
			while(rs.next()){
				result=String.valueOf(rs.getInt(1));

			}

			rs.close();
			closeConnection();
			return result;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			return result;
		}
	}
	
	
	public String getNumberOfPendingCustOrders(String marketId,String userName){
		
	
		String result="";
		try{
			connectToDB();
			String cmpgnNo=getCampaignNo(marketId,userName);
			String cmpgnYr=getCampaignYrNo(marketId,userName);
			
			System.out.println(cmpgnNo);
			System.out.println(cmpgnYr);
			String query="select count(*) from cust_ord_load where acct_nr='"+userName+"' and mrkt_id='"+marketId+"' and ORD_STUS_CD='CNEW' and CMPGN_NR="+cmpgnNo+" and CMPGN_YR_NR="+cmpgnYr;
			System.out.println(query);
			ResultSet rs=runQuery(query);
			while(rs.next()){
				result=String.valueOf(rs.getInt(1));
				System.out.println(result);

			}

			rs.close();
			closeConnection();
			return result;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			return result;
		}
	}
	
	public String getNumberOfHeldOrders(String userName){

		String result="";
		try{
			connectToDB();
			ResultSet rs=runQuery("select count(*) from rep_ord_trkg where acct_nr="+userName+" and mrkt_id='"+marketid+"'and shpng_STUS_CD='CRDHLD'");
			while(rs.next()){
				result=String.valueOf(rs.getInt(1));

			}

			rs.close();
			closeConnection();
			return result;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			return result;
		}
	}
	public int getCampaignNumber(String userName) throws ClassNotFoundException, SQLException{

		int cmpgnNr = 0;
		String query="";
		System.out.println(marketid);
		System.out.println(userName);
		switch(userName){
		case "Account Number":
			query="SELECT DISTINCT MIN(S.CMPGN_NR) FROM rep r,rep_addr ra, user_id u, slng_day_mlpln s WHERE u.mrkt_id =" + marketid + " AND u.mrkt_id = r.mrkt_id AND u.acct_nr = r.acct_nr and r.mrkt_id = ra.mrkt_id and r.acct_nr = ra.acct_nr AND r.acct_nr=" + userName + " AND r.mrkt_id = s.mrkt_id AND r.creatn_lvl_cd = s.fld_lvl_cd AND r.creatn_lvl_nr = s.fld_lvl_nr AND (s.ONLN_ORD_SBMSN_DT)>=SYSDATE";
			break;
		case "Email":
			query="SELECT DISTINCT MIN(S.CMPGN_NR) FROM rep r,rep_addr ra, user_id u, slng_day_mlpln s WHERE u.mrkt_id =" + marketid + " AND u.mrkt_id = r.mrkt_id AND u.acct_nr = r.acct_nr and r.mrkt_id = ra.mrkt_id and r.acct_nr = ra.acct_nr AND r.email_addr_txt=" + userName + " AND r.mrkt_id = s.mrkt_id AND r.creatn_lvl_cd = s.fld_lvl_cd AND r.creatn_lvl_nr = s.fld_lvl_nr AND (s.ONLN_ORD_SBMSN_DT)>=SYSDATE";
			break;

		case "Phone Number":
			query="SELECT DISTINCT MIN(S.CMPGN_NR) FROM rep r,rep_addr ra, user_id u, slng_day_mlpln s WHERE u.mrkt_id =" + marketid + " AND u.mrkt_id = r.mrkt_id AND u.acct_nr = r.acct_nr and r.mrkt_id = ra.mrkt_id and r.acct_nr = ra.acct_nr AND r.mobile_phon_nr=" + userName + " AND r.mrkt_id = s.mrkt_id AND r.creatn_lvl_cd = s.fld_lvl_cd AND r.creatn_lvl_nr = s.fld_lvl_nr AND (s.ONLN_ORD_SBMSN_DT)>=SYSDATE";
			break;


		}

		ResultSet rs=runQuery(query);

		while(rs.next()){
			cmpgnNr  = rs.getInt("MIN(S.CMPGN_NR)");
			System.out.println("cmpgn_nr: " + cmpgnNr);
		}
		stmt.close();
		rs.close();
		closeConnection();
		return cmpgnNr;
	}



	public String verifyOrderinTable(ReadExcel readExcel,String orderId) {
		String status=null;
		try{
			connectToDB();
			String query=readExcel.getValue("verifyOrderinTable");
			query=query.replace("?", orderId);
			//String query="select *from ord where mrkt_id=" + marketid + " and ord_id="+orderId;
			ResultSet rs=runQuery(query);
			while(rs.next()){
				status= rs.getString("ord_stus_cd");
			}
			stmt.close();
			rs.close();
			closeConnection();
			return status;
		}
		catch(Exception e){
			return status;
		}

	}
//
	public ArrayList<String> getLineNumber() {
		ArrayList<String> LineNumbers=new ArrayList<String>();
		try{
			connectToDB();

			String query="select distinct line_nr from lcl_line_nr where mrkt_id=" + marketid + " and cmpgn_yr_nr=2017 and cmpgn_nr=14 and for_sale_ind='Y' and prod_typ='REG' and dscntnd_item_ind='N' and REP_IND='Y' and rowNum<=1000";
			System.out.println("executing query");
			ResultSet rs=runQuery(query);
			System.out.println("query successfully got executed");
			System.out.println("Total records in result set " + rs.getRow());
			while(rs.next()){
				LineNumbers.add(rs.getString("line_nr"));
			}
			stmt.close();
			rs.close();
			closeConnection();
			return LineNumbers;
		}
		catch(Exception e){
			return LineNumbers;
		}

	}
	
	public String checkTempPassword(String username){

		String result=new String("");
		
		try{
			connectToDB();
			ResultSet rs=runQuery("select temp_pswd_ind from user_id  where acct_nr="+username+" and  mrkt_id="+marketid);
			while(rs.next()){

				result=rs.getString(1);



			}

			rs.close();
			closeConnection();
			return result;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			return result;
		}}

	
	public ArrayList<String> getProfileName(String userName,String loginType){

		String name="";
		String titleCode="";
		ArrayList<String> result=new ArrayList<String>();




		try{
			ResultSet rs;
			connectToDB();

			switch(loginType){

			case "Account Number":
				rs=runQuery("select TITL_CD,FRST_NM from rep where acct_nr="+userName+" and  mrkt_id="+marketid);
				while(rs.next()){
					result.add(rs.getString(1));
					result.add(rs.getString(2));

				}
				rs.close();
				break;
			case "Email":
				rs=runQuery("select TITL_CD,FRST_NM from rep where email_addr_txt="+userName+" and  mrkt_id="+marketid);
				while(rs.next()){
					result.add(rs.getString(1));
					result.add(rs.getString(2));
				}
				rs.close();
				break;
			case "Phone Number":
				rs=runQuery("select TITL_CD,FRST_NM from rep where mobile_phon_nr="+userName+" and  mrkt_id="+marketid);
				while(rs.next()){
					result.add(rs.getString(1));
					result.add(rs.getString(2));
				}
				rs.close();
				break;





			}









			closeConnection();
			return result;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			return result;
		}
	}	
public String getCampaignNo(String marketID,String userName) 
       {
              String campaigNo="";
            
              campaigNo=getDbDetails("select CMPGN_NR from slng_day_mlpln where ONLN_ORD_SBMSN_DT>sysdate and mrkt_id='"+marketID+"'  and fld_lvl_cd in (select CREATN_LVL_CD from rep where acct_nr='"+userName+"') and rownum=1 order by CMPGN_NR,CMPGN_DAY_NR");
              return campaigNo;
                           
              
       }
       
       public String getCampaignYrNo(String marketID,String userName) 
       {
              String campaigYrNo="";
        
              campaigYrNo=getDbDetails("select CMPGN_YR_NR from slng_day_mlpln where ONLN_ORD_SBMSN_DT>sysdate and mrkt_id='"+marketID+"'and fld_lvl_cd in (select CREATN_LVL_CD from rep where acct_nr='"+userName+"') and rownum=1 order by CMPGN_NR,CMPGN_DAY_NR");
              return campaigYrNo;
                           
              
       }
       public String OSAUser(String marketID) {
    	   String OSAuser;
    	   long pl=8;
	   	   long ro,bg,hu=7;
	   	   long cz=9;
    	   if(marketID.equals("30"))
    		   
    		   OSAuser=getDbDetails("select * from (SELECT r.acct_nr FROM user_id u, rep r WHERE r.mrkt_id = "+ marketID +" AND r.mrkt_id = u.mrkt_id AND r.acct_nr = u.acct_nr AND r.acct_typ = 'REP' AND u.pswrd_txt = 'a906449d5769fa7361d7ecc6aa3f6d28' AND u.last_vist_dt IS NOT NULL AND u.logn_atmpt_cnt = 0 AND r.ACCT_STUS_CD='A' ORDER BY u.last_vist_dt DESC) where rownum=1");
    	   else
    		   OSAuser=getDbDetails("select * from (SELECT r.acct_nr FROM user_id u, rep r WHERE r.mrkt_id = "+ marketID +" AND r.mrkt_id = u.mrkt_id AND r.acct_nr = u.acct_nr AND r.acct_typ = 'REP' AND u.pswrd_txt = 'a906449d5769fa7361d7ecc6aa3f6d28' AND u.last_vist_dt IS NOT NULL AND u.logn_atmpt_cnt = 0 ORDER BY u.last_vist_dt DESC) where rownum=1");
    	   long count=(OSAuser.chars()
                   .filter(Character::isDigit)
                   .count());
    	   if (marketID.equals("30")) 
    		   OSAuser = String.format("%08d", Integer.parseInt(OSAuser));
    	   
    	   else if (marketID.equals("24"))
    		   OSAuser = String.format("%09d", Integer.parseInt(OSAuser));
    	   else
    		   OSAuser = String.format("%07d", Integer.parseInt(OSAuser));
    	   
    	   //System.out.println(OSAuser);
    	   return OSAuser;
   	}
       
       public String getSingleValidProduct(String marketID,String userName) {
              String campaigYrNo=getDbDetails("select CMPGN_YR_NR from slng_day_mlpln where ONLN_ORD_SBMSN_DT>sysdate and mrkt_id='"+marketID+"'  and fld_lvl_cd in (select CREATN_LVL_CD from rep where acct_nr='"+userName+"') and rownum=1 order by CMPGN_NR,CMPGN_DAY_NR ");
              String campaigNo=getDbDetails("select CMPGN_NR from slng_day_mlpln where ONLN_ORD_SBMSN_DT>sysdate and mrkt_id='"+marketID+"'  and fld_lvl_cd in (select CREATN_LVL_CD from rep where acct_nr='"+userName+"') and rownum=1 order by CMPGN_NR,CMPGN_DAY_NR ");
              System.out.println("select LINE_NR from lcl_line_nr where prod_typ='REG' and for_sale_ind='Y' and mrkt_id='"+marketID+"' and CMPGN_YR_NR ='"+campaigYrNo+"'and CMPGN_NR='"+campaigNo+"'AND REP_IND='Y' AND COORDTR_IND='Y' AND MGR_IND='Y' AND ITEM_FSC_NR IN (select ITEM_FSC_NR from DSTRBTN_CNTR_SBSTTN_PAI where PAI_DMND_DT=trunc(sysdate)AND  AVLBLTY_CD='GRN' AND MRKT_ID='"+marketID+"' ) AND ROWNUM=1");
           
              String product=getDbDetails("select LINE_NR from lcl_line_nr where prod_typ='REG' and for_sale_ind='Y' and mrkt_id='"+marketID+"' and CMPGN_YR_NR ='"+campaigYrNo+"'and CMPGN_NR='"+campaigNo+"'AND REP_IND='Y' AND COORDTR_IND='Y' AND MGR_IND='Y' AND ITEM_FSC_NR IN (select ITEM_FSC_NR from DSTRBTN_CNTR_SBSTTN_PAI where PAI_DMND_DT=trunc(sysdate)AND  AVLBLTY_CD='GRN' AND MRKT_ID='"+marketID+"' ) AND ROWNUM=1");
              
              return product;
       }
       
       
       public  ArrayList<String> getMultipleUnavailableProducts_noAltProds(String marketID,String userName) {
    	   String campaigYrNo=getDbDetails("select CMPGN_YR_NR from slng_day_mlpln where ONLN_ORD_SBMSN_DT>sysdate and mrkt_id='"+marketID+"'  and fld_lvl_cd in (select CREATN_LVL_CD from rep where acct_nr='"+userName+"') and rownum=1 order by CMPGN_NR,CMPGN_DAY_NR ");
           String campaigNo=getDbDetails("select CMPGN_NR from slng_day_mlpln where ONLN_ORD_SBMSN_DT>sysdate and mrkt_id='"+marketID+"'  and fld_lvl_cd in (select CREATN_LVL_CD from rep where acct_nr='"+userName+"') and rownum=1 order by CMPGN_NR,CMPGN_DAY_NR ");
           ArrayList<String> products=new ArrayList<String>();
           
           try {
                  connectToDB();
                  String query="select LINE_NR from lcl_line_nr where prod_typ='REG' and for_sale_ind='Y' and mrkt_id="+marketID+" and CMPGN_YR_NR ="+campaigYrNo
                  		+ "and CMPGN_NR="+campaigNo+" AND LMTD_QTY_OFFR_IND='N' AND REP_IND='Y' AND COORDTR_IND='Y' AND MGR_IND='Y' AND ITEM_FSC_NR IN "
                  		+ "(select ITEM_FSC_NR from DSTRBTN_CNTR_SBSTTN_PAI where PAI_DMND_DT=trunc(sysdate)AND  AVLBLTY_CD='S' AND MRKT_ID="+marketID+"  and Sbsttn_Item_Fsc_Nr is null) "
                  		+ "AND ROWNUM<=20";
                  System.out.println(query);
                  ResultSet rs=runQuery(query);
           while(rs.next()) {
                  String product=rs.getString("LINE_NR");
                  products.add(product);
           }
           stmt.close();
           rs.close();
           closeConnection();
           }
           catch(Exception e){
                  System.out.println("Some error occured");
                  System.out.println(e.getMessage());
           }
           
           return products;
    }
       public ArrayList<String> getMultipleInvalidProducts(String marketID,String userName) {
           String campaigYrNo=getDbDetails("select CMPGN_YR_NR from slng_day_mlpln where ONLN_ORD_SBMSN_DT>sysdate and mrkt_id='"+marketID+"'  and fld_lvl_cd in (select CREATN_LVL_CD from rep where acct_nr='"+userName+"') and rownum=1 order by CMPGN_NR,CMPGN_DAY_NR ");
           String campaigNo=getDbDetails("select CMPGN_NR from slng_day_mlpln where ONLN_ORD_SBMSN_DT>sysdate and mrkt_id='"+marketID+"'  and fld_lvl_cd in (select CREATN_LVL_CD from rep where acct_nr='"+userName+"') and rownum=1 order by CMPGN_NR,CMPGN_DAY_NR ");
           ArrayList<String> products=new ArrayList<String>();
           
           try {
                  connectToDB();
                  String query="select LINE_NR from lcl_line_nr where line_nr not in "
           		+ "(select LINE_NR from lcl_line_nr where prod_typ='REG' and for_sale_ind='Y' and"
           		+ "mrkt_id="+marketID+" and CMPGN_YR_NR ="+campaigYrNo+" and CMPGN_NR="+campaigNo
           		+ "AND LMTD_QTY_OFFR_IND='N' "
           		+ "AND REP_IND='Y' AND COORDTR_IND='Y' AND MGR_IND='Y') and mrkt_id"+marketID+" and prod_typ='REG' and for_sale_ind='Y'"
           		+ "AND ROWNUM<=30";
                  System.out.println(query);
                  ResultSet rs=runQuery(query);
           while(rs.next()) {
                  String product=rs.getString("LINE_NR");
                  products.add(product);
           }
           stmt.close();
           rs.close();
           closeConnection();
           }
           catch(Exception e){
                  System.out.println("Some error occured");
                  System.out.println(e.getMessage());
           }
           
           return products;
    }

       
       public String getProductName(String marketID,String lineNum,String userName) {
    	   String campaigYrNo=getDbDetails("select CMPGN_YR_NR from slng_day_mlpln where ONLN_ORD_SBMSN_DT>sysdate and mrkt_id='"+marketID+"'  and fld_lvl_cd in (select CREATN_LVL_CD from rep where acct_nr='"+userName+"') and rownum=1 order by CMPGN_NR,CMPGN_DAY_NR ");
           String campaigNo=getDbDetails("select CMPGN_NR from slng_day_mlpln where ONLN_ORD_SBMSN_DT>sysdate and mrkt_id='"+marketID+"'  and fld_lvl_cd in (select CREATN_LVL_CD from rep where acct_nr='"+userName+"') and rownum=1 order by CMPGN_NR,CMPGN_DAY_NR ");
           
           String productName=getDbDetails("Select line_nr_nm from lcl_line_nr_lang where mrkt_id='"+marketID+"' and line_nr='"+lineNum+"' and cmpgn_yr_nr='"+campaigYrNo+"'and cmpgn_nr='"+campaigNo+"'");
           return productName;
    }
       
       
       /** This function will return 30 product numbers in an arraylist of String */
       public ArrayList<String> getMultipleProducts(String marketID,String userName) {
    	   ArrayList<String> products=new ArrayList<String>();
    	   System.out.println(marketID);
    	   String campaigYrNo=getDbDetails("select CMPGN_YR_NR from slng_day_mlpln where ONLN_ORD_SBMSN_DT>sysdate and mrkt_id='"+marketID+"'  and fld_lvl_cd in (select CREATN_LVL_CD from rep where acct_nr='"+userName+"') and rownum=1 order by CMPGN_NR,CMPGN_DAY_NR ");
           String campaigNo=getDbDetails("select CMPGN_NR from slng_day_mlpln where ONLN_ORD_SBMSN_DT>sysdate and mrkt_id='"+marketID+"'  and fld_lvl_cd in (select CREATN_LVL_CD from rep where acct_nr='"+userName+"') and rownum=1 order by CMPGN_NR,CMPGN_DAY_NR ");
           
    	  
              
              try {
                     connectToDB();
               
                        
                     String query="select LINE_NR from lcl_line_nr where prod_typ='REG' and for_sale_ind='Y' and mrkt_id='"+marketID+"' and CMPGN_YR_NR ='"+campaigYrNo+"'and CMPGN_NR='"+campaigNo+"' AND REP_IND='Y' AND COORDTR_IND='Y' AND MGR_IND='Y' AND ITEM_FSC_NR IN (select ITEM_FSC_NR from DSTRBTN_CNTR_SBSTTN_PAI where PAI_DMND_DT=trunc(sysdate)AND  AVLBLTY_CD='GRN' AND MRKT_ID='"+marketID+"')AND ROWNUM<=30";
                     System.out.println(query);
                     ResultSet rs=runQuery(query);
              while(rs.next()) {
                     String product=rs.getString("LINE_NR");
                     products.add(product);
              }
              stmt.close();
              rs.close();
              closeConnection();
              }
              catch(Exception e){
                     System.out.println("Some error occured");
                     System.out.println(e.getMessage());
              }
              
              return products;
       }

       public ArrayList<String> getDBDetails(String marketID,String userName) {
    	   ArrayList<String> products=new ArrayList<String>();
    	   String campaigYrNo=getDbDetails("select * from (select CMPGN_YR_NR from slng_day_mlpln where ONLN_ORD_SBMSN_DT>sysdate and mrkt_id="+marketID+" and fld_lvl_cd in (select CREATN_LVL_CD from rep where acct_nr='"+userName+"')  order by CMPGN_NR,CMPGN_DAY_NR ) where rownum=1");
     	   	String campaigNo=getDbDetails("select * from (select CMPGN_NR from slng_day_mlpln where ONLN_ORD_SBMSN_DT>sysdate and mrkt_id="+marketID+" and fld_lvl_cd in (select CREATN_LVL_CD from rep where acct_nr='"+userName+"')  order by CMPGN_NR,CMPGN_DAY_NR ) where rownum=1"); 
            
    	  
              
              try {
                     connectToDB();
               
                        
                     String query="select LINE_NR from lcl_line_nr where prod_typ='REG' and for_sale_ind='Y' and mrkt_id='"+marketID+"' and CMPGN_YR_NR ='"+campaigYrNo+"'and CMPGN_NR='"+campaigNo+"' AND REP_IND='Y' AND COORDTR_IND='Y' AND MGR_IND='Y' AND ITEM_FSC_NR IN (select ITEM_FSC_NR from DSTRBTN_CNTR_SBSTTN_PAI where PAI_DMND_DT=trunc(sysdate)AND  AVLBLTY_CD='GRN' AND MRKT_ID='"+marketID+"')AND ROWNUM<=30";
                     System.out.println(query);
                     ResultSet rs=runQuery(query);
              while(rs.next()) {
                     String product=rs.getString("LINE_NR");
                     products.add(product);
              }
              stmt.close();
              rs.close();
              closeConnection();
              }
              catch(Exception e){
                     System.out.println("Some error occured");
                     System.out.println(e.getMessage());
              }
              
              return products;
       }
       public void insertTable(String query)
       {
                    try {
                            connectToDB();
                     
                            PreparedStatement ps=conn.prepareStatement(query);
                            
                            
                            ps.executeUpdate();
                            conn.commit();
                            System.out.println("Insert Successful");
                    } catch (ClassNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                     } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                     }
       }
       public ArrayList<String> getMultipleValues(String query,List<String> cond ){
    	   ArrayList<String> values=new ArrayList<String>();
       int i=1;
   	String temp;
   	ResultSet rs;
   	String result = null;
   	try {
   		connectToDB();
   		PreparedStatement ps=conn.prepareStatement(query);
   		while(i<=cond.size())
   		{
   			temp=cond.get(i-1);
   			ps.setString(i, temp);
   			i++;
   		}
   		rs=ps.executeQuery();


   		while(rs.next()){
   			String value=rs.getString(1);
            System.out.println("DB:" +value);
            values.add(value);
   		}

   		//stmt.close();
   		rs.close();
   		closeConnection();
   	} catch (ClassNotFoundException | SQLException e) {
   		// TODO Auto-generated catch block
   		e.printStackTrace();
   	}
   	return values;
       }
       public ArrayList<String> getMultipleValues(String query ){
    	   ArrayList<String> values=new ArrayList<String>();
    	   
    	  
              
              try {
                     connectToDB();
               
                       
                     ResultSet rs=runQuery(query);
              while(rs.next()) {
                     String value=rs.getString(1);
                     System.out.println("DB:" +value);
                     values.add(value);
              }
              stmt.close();
              rs.close();
              closeConnection();
              }
              catch(Exception e){
                     System.out.println("Some error occured");
                     System.out.println(e.getMessage());
              }
              
              return values;
       }

	public ArrayList<String> getAccountBalance(String userName,String loginType){


		ArrayList<String> result=new ArrayList<String>();
		try{
			ResultSet rs;
			connectToDB();
			int i=1;
			switch(loginType){

			case "Account Number":
				rs=runQuery("select CURR_BAL_AMT,AVON_BANK_ACCT_NR,MAX_CRDT_AMT,DUE_BAL_AMT ,PENDNG_CRDTS_AMT ,(CURR_BAL_AMT - MAX_CRDT_AMT) as remainingCreditLimit from rep where acct_nr="+userName+" and  mrkt_id="+marketid);

				while(rs.next()){
					result.add(String.valueOf(rs.getDouble(1)));
					result.add(rs.getString(2));
					result.add(String.valueOf(rs.getDouble(3)));
					result.add(String.valueOf(rs.getDouble(4)));
					result.add(String.valueOf(rs.getDouble(5)));
					result.add(String.valueOf(rs.getDouble(6)));
					
				}
				
				rs.close();
				break;
			case "Email":
				rs=runQuery("select CURR_BAL_AMT,AVON_BANK_ACCT_NR,MAX_CRDT_AMT,DUE_BAL_AMT ,PENDNG_CRDTS_AMT ,PENDNG_PYMT_AMT,REP_PNLTY_AMT ,(CURR_BAL_AMT - MAX_CRDT_AMT) as remainingCreditLimit from rep where email_addr_txt="+userName+" and  mrkt_id="+marketid);

				while(rs.next()){
					result.add(String.valueOf(rs.getDouble(i)));
					i++;

				}
				rs.close();
				break;


			}
			closeConnection();

			return result;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			return result;
		}
	}
	public String getOverdueBalance(ReadExcel readExcel,String userName){

		String result=new String("");
		
		try{
			connectToDB();
			String query=readExcel.getValue("getOverdueBalance");
			query=query.replace("?", userName);
			ResultSet rs=runQuery(query);
			while(rs.next()){

				result=rs.getString(1);

			}


			rs.close();
			closeConnection();
			return result;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			return result;
		}
	}

	public String getNumberOfOverdueTransaction(ReadExcel readExcel,String userName){



		String result="";

		

		try{

			connectToDB();
			
			String query=readExcel.getValue("getNumberOfOverdueTransaction");
			query=query.replace("?", userName);
			ResultSet rs=runQuery(query);

			while(rs.next()){



				result=String.valueOf(rs.getInt(1));

			}





			rs.close();

			closeConnection();

			return result;

		}

		catch(Exception e){

			System.out.println(e.getMessage());

			return result;

		}

	}

	public String getNumberOfUpcomingdueTransaction(ReadExcel readExcel,String userName){

		String result="";
		try{
			connectToDB();
			String query=readExcel.getValue("getNumberOfUpcomingdueTransaction");
			query=query.replace("?", userName);
			ResultSet rs=runQuery(query);
			while(rs.next()){
				result=String.valueOf(rs.getInt(1));

			}

			rs.close();
			closeConnection();
			return result;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			return result;
		}
	}


	public String getLatestDueDate(ReadExcel readExcel,String userName){



		String result="";



		try{

			connectToDB();
			String query=readExcel.getValue("getLatestDueDate");
			query=query.replace("?", userName);
			ResultSet rs=runQuery(query);

			while(rs.next()){

				result=rs.getString(1);

			}





			rs.close();

			closeConnection();

			return result;

		}

		catch(Exception e){

			System.out.println(e.getMessage());

			return result;

		}

	}



	public Map<Integer,List<String>> getTransactions(String userName){

		Map<Integer,List<String>> transactionData= new TreeMap<Integer,List<String>>();
		try{
			connectToDB();
			
			
			ResultSet rs=runQuery("select BRCHR_YR_NR,BRCHR_CMPGN_NR,TRAN_DCMNT_NR,TRAN_DT,TRAN_DUE_DT,TRAN_AMT from rep_tran  where acct_nr="+userName+" and  mrkt_id="+marketid+" and tran_due_dt<sysdate and tran_due_dt is not null  order by TRAN_DT desc");

			int key=1;
			while(rs.next()){


				List<String> list= new ArrayList<String>();
				//map.put(rs.getString(1),rs.getString("temp_pswd_ind"));
				list.add(rs.getString(1)+rs.getString(2));
				System.out.println(rs.getString(1)+rs.getString(2));
				list.add(rs.getString(3));
				System.out.println(rs.getString(3));
				list.add(rs.getString(4));
				System.out.println(rs.getString(4));
				list.add(rs.getString(5));
				System.out.println(rs.getString(5));
				list.add(rs.getString(6));
				System.out.println(rs.getString(6));
				transactionData.put(key, list);
				key++;

			}
			rs.close();
			closeConnection();

		}catch(Exception e){
			System.out.println(e.getMessage());

		}

		return transactionData;  

	}

	public ArrayList<String> getBalanceDetails(String userName,String loginType){





		ArrayList<String> result=new ArrayList<String>();

		try{

			ResultSet rs;

			ResultSet rs_1;

			Double value=null;

			connectToDB();

			int i=1;

			switch(loginType){



			case "Account Number":

				rs=runQuery("select CURR_BAL_AMT,DUE_BAL_AMT,AVLBL_CRDT_AMT,AVON_BANK_ACCT_NR from rep where acct_nr="+userName+" and  mrkt_id="+marketid);

				rs_1=runQuery("SELECT SUM (TO_NUMBER (NVL (GRS_SLS_AMT, 0) + NVL (FRGHT_FEE_AMT, 0))) AS SUBORDVAL FROM REP_ORD_TRKG WHERE mrkt_id="+marketid+ "AND acct_nr = "+userName+" AND SHPNG_STUS_CD IN ('ENTRD', 'CRDHLD')  AND ORD_TYP IN ('REG')");    

				while(rs.next()){

					result.add(String.valueOf(rs.getDouble(1)));


					result.add(rs.getString(2));

					value=rs.getDouble(3);

					result.add(String.valueOf(rs.getDouble(3)));

					result.add(rs.getString(4));

				}

				while(rs_1.next()){



					result.add(String.valueOf(value-rs_1.getDouble(1)));



				}



				rs.close();

				break;



			case "Email":

				rs=runQuery("select CURR_BAL_AMT,DUE_BAL_AMT,AVLBL_CRDT_AMT,AVON_BANK_ACCT_NR from rep where email_addr_txt="+userName+" and  mrkt_id="+marketid);

				rs_1=runQuery("SELECT SUM (TO_NUMBER (NVL (GRS_SLS_AMT, 0) + NVL (FRGHT_FEE_AMT, 0))) AS SUBORDVAL FROM REP_ORD_TRKG WHERE mrkt_id="+marketid+ "AND acct_nr = "+userName+" AND SHPNG_STUS_CD IN ('ENTRD', 'CRDHLD')  AND ORD_TYP IN ('REG')");    

				while(rs.next()){

					result.add(String.valueOf(rs.getDouble(1)));

					result.add(rs.getString(2));

					value=rs.getDouble(3);

					result.add(String.valueOf(rs.getDouble(3)));

					result.add(rs.getString(4));

			
				}

				while(rs_1.next()){



					result.add(String.valueOf(value-rs_1.getDouble(1)));



				}



				rs.close();

				break;



			}

			closeConnection();



			return result;

		}

		catch(Exception e){

			System.out.println(e.getMessage());

			return result;

		}

	}

	//new method
	//returns the count of payment records of the user

	public int getNumberOfPayments(String userName,String loginType){

		int result=0;
		ResultSet rs;

		try{
			connectToDB();

			switch(loginType){
			case "Account Number":

				rs=runQuery("select count(*) from rep_tran where mrkt_id="+marketid+" and pymt_ind='Y' and acct_nr="+userName);
				while(rs.next()){
					result=rs.getInt(1);
				}




				rs.close();
				break;


			case "Email":

				rs=runQuery("select count(*) from rep_tran where mrkt_id='"+marketid+"' and pymt_ind='Y' and email_addr_txt='"+userName);
				while(rs.next()){
					result=rs.getInt(1);
				}




				rs.close();
				break;
			}

			closeConnection();
			return result;


		}
		catch(Exception e){
			System.out.println(e.getMessage());
			return result;
		}}


	public int getNumberOfReturns(String userName,String loginType){

		int result=0;
		ResultSet rs;

		try{
			connectToDB();

			switch(loginType){
			case "Account Number":

				rs=runQuery("select count(*) from rep_tran where mrkt_id="+marketid+" and pymt_ind='N' and tran_amt < 0 and acct_nr="+userName);
				while(rs.next()){
					result=rs.getInt(1);
				}




				rs.close();
				break;


			case "Email":

				rs=runQuery("select count(*) from rep_tran where mrkt_id='"+marketid+"' and pymt_ind='N' and tran_amt < 0 and email_addr_txt='"+userName);
				while(rs.next()){
					result=rs.getInt(1);
				}




				rs.close();
				break;
			}

			closeConnection();
			return result;


		}
		catch(Exception e){
			System.out.println(e.getMessage());
			return result;
		}}
	public ArrayList<String[]> getOrderDetails(String loginType,String userName){
		ArrayList<String[]> list=new ArrayList<String[]>();
		String[] m=new String[7];
		int cmpgnNr = 0;
		String Query="";	

		try{
			connectToDB();

			switch(loginType){
			case "Account Number":
				Query="SELECT u.CMPGN_YR_NR,u.CMPGN_NR,u.ORD_NM,b.ORD_MGMT_IP_DT as Order_date,u.ORD_ID,b.INVC_NR,b.NR_OF_CARTNS_NR from ord u  inner join  rep_ord_trkg b  on u.mrkt_id = b.mrkt_id "
						+ "where u.mrkt_id="+marketid+" and u.acct_nr="+userName;

				break;
			case "Email":
				Query="SELECT u.CMPGN_YR_NR,u.CMPGN_NR,u.ORD_NM,b.ORD_MGMT_IP_DT as Order_date,u.ORD_ID,b.INVC_NR,b.NR_OF_CARTNS_NR from ord u  inner join  rep_ord_trkg b  on u.mrkt_id = b.mrkt_id "
						+ "where u.mrkt_id="+marketid+" and u.email_addr_txt="+userName;
				break;


			}

			ResultSet rs=runQuery(Query);

			while(rs.next()){
				m[0]= rs.getString(1)+" "+rs.getString(2);
				m[1]=rs.getString(3);
				m[2]=rs.getString(4);
				m[3]=rs.getString(5);
				m[4]=rs.getString(6);
				m[5]=rs.getString(7);
				m[6]=rs.getString(8);



			}
			list.add(m);

			stmt.close();
			rs.close();
			closeConnection();

		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return list;
	}

	public List<String> getUplineDetails(String orderName,String userName){
		List<String> list=new ArrayList<String>();
		String query="";	
		ResultSet rs;

		try{
			connectToDB();
			query="SELECT b.acct_typ,b.frst_nm,b.last_nm from ord u  inner join  rep b  on u.mrkt_id = b.mrkt_id "
					+ "where u.mrkt_id="+marketid+" and u.ord_id="+orderName+" and b.acct_nr=u.ORD_CREATR_ID";


			rs=runQuery(query);

			while(rs.next()){
				list.add(rs.getString(1));
				list.add(rs.getString(2));
				list.add(rs.getString(3));
			}
			query="Select acct_typ from rep where acct_nr="+userName;
			rs=runQuery(query);

			while(rs.next()){
				list.add(rs.getString(1));

				stmt.close();
				rs.close();
				closeConnection();
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return list;
	}
	/* returns account number of the latest invoice request*/
	public int getLatestInvoiceRequest(String userName){

		String query="";
		int result=0;
		ResultSet rs;

		try{
			connectToDB();
			query="select count(*) from rqst where mrkt_id="+marketid+" and RQST_TYP='EINVL' and acct_nr="+userName;

			
			rs=runQuery(query);

			while(rs.next()){
				result=rs.getInt(1);
			}
			System.out.println(result);

			stmt.close();
			rs.close();
			closeConnection();

		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return result;
	}



	public String getDbDetails(String query){
		ResultSet rs;
		String result = null;
			try {
				connectToDB();
				rs=runQuery(query);
    
				while(rs.next()){
					result=rs.getString(1);
				}
    
				stmt.close();
				rs.close();
				closeConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
    	}
	
    public String getDbDetails(String query,List<String> cond){
    	int i=1;
    	String temp;
    	ResultSet rs;
    	String result = null;
    	try {
    		connectToDB();
    		PreparedStatement ps=conn.prepareStatement(query);
    		while(i<=cond.size())
    		{
    			temp=cond.get(i-1);
    			ps.setString(i, temp);
    			i++;
    		}
    		rs=ps.executeQuery();


    		while(rs.next()){
    			result=rs.getString(1);
    		}

    		//stmt.close();
    		rs.close();
    		closeConnection();
    	} catch (ClassNotFoundException | SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return result;
    }
    
    
	public void updateDbDetails(String query){
		ResultSet rs;
	
			try {
				connectToDB();
				rs=runQuery(query);

					stmt.close();
					rs.close();
					closeConnection();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    	}
	
    public List getMultippleDbColumnDetails(String query){
    	ResultSet rs;
    	List<String> resultList=new ArrayList<String>();
    	try {
    		connectToDB();
    		rs=runQuery(query);
    		int colCount=1;
    		ResultSetMetaData rsmd=rs.getMetaData();
    		int columnCount=rsmd.getColumnCount();
    		while(rs.next()){
    			while(colCount<=columnCount)
    				resultList.add(rs.getString(colCount++));
    		}
    		stmt.close();
    		rs.close();
    		closeConnection();
    	} catch (ClassNotFoundException | SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return resultList;
    }
    
    
    public List<String> getMultippleDbColumnDetails(String query, List<String> cond){
    	ResultSet rs;
    	List<String> resultList=new ArrayList<String>();
    	try {
    		connectToDB();
    		PreparedStatement ps=conn.prepareStatement(query);
    		int i=1;
    		String temp;
    		while(i<=cond.size())
    		{
    			temp=cond.get(i-1);
    			ps.setString(i, temp);
    			i++;
    		}
    		rs=ps.executeQuery();
    		
    		ResultSetMetaData rsmd=rs.getMetaData();
    		int columnCount=rsmd.getColumnCount();
    		while(rs.next()){
    			int colCount=1;
    			while(colCount<=columnCount)
    				resultList.add(rs.getString(colCount++));
    		}
    		ps.close();
    		rs.close();
    		closeConnection();
    	} catch (ClassNotFoundException | SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return resultList;
    	}
    public Map<Integer,List<String>> getMultipleDbRowDetails(String query, List<String> cond){
    	ResultSet rs;
    	Map<Integer,List<String>> rowData= new TreeMap<Integer,List<String>>();
    	int key=1;
    	try {
    		connectToDB();
    		PreparedStatement ps=conn.prepareStatement(query);
    		int i=1;
    		String temp;
    		while(i<=cond.size())
    		{
    			temp=cond.get(i-1);
    			ps.setString(i, temp);
    			i++;
    		}
    		rs=ps.executeQuery();
    	
    		ResultSetMetaData rsmd=rs.getMetaData();
    		int columnCount=rsmd.getColumnCount();
    		while(rs.next()){
    			int colCount=1;
                    List<String> resultList=new ArrayList<String>();
                    while(colCount<=columnCount) {
                    	System.out.println("DB:" +rs.getString(colCount));
                                    resultList.add(rs.getString(colCount++));
                    }
                    rowData.put(key,resultList);
                    key++;
    		}
    		ps.close();
    		rs.close();
    		closeConnection();
    	} catch (ClassNotFoundException | SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return rowData;
    	}
 

    public Map<Integer,List<String>> getMultipleDbRowDetails(String query){
    	ResultSet rs;
    	Map<Integer,List<String>> rowData= new TreeMap<Integer,List<String>>();
    	int key=1;
    	try {
    		connectToDB();
    		
    		
    		rs=runQuery(query);
    	
    		ResultSetMetaData rsmd=rs.getMetaData();
    		int columnCount=rsmd.getColumnCount();
    		while(rs.next()){
    			int colCount=1;
                    List<String> resultList=new ArrayList<String>();
                    while(colCount<=columnCount) {
                    	System.out.println("DB:" +rs.getString(colCount));
                                    resultList.add(rs.getString(colCount++));
                    }
                    rowData.put(key,resultList);
                    key++;
    		}
    		
    		rs.close();
    		closeConnection();
    	} catch (ClassNotFoundException | SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return rowData;
    	}
    
    public int getLoginAttemptCount(String userName) {
        int result=0;
        try {
              connectToDB();

              ResultSet rs=runQuery("select LOGN_ATMPT_CNT from USER_ID where USER_ID="+userName+" and ACCT_NR="+userName+"and mrkt_id="+marketid );

              while(rs.next()){
                    result=rs.getInt(1);
              }
              rs.close();

              closeConnection();
        }catch(Exception e){
               System.out.println(e.getMessage());
                   
        }
        return result;


  }


public int getAcceptedTermsAndConditionsDocCount(String userName) {
        int result=0;
        try {
              connectToDB();

              ResultSet rs=runQuery("select count(*) from USER_AGRMNT where USER_ID="+userName+" and AGRMNT_ACPTD_IND='Y' and  mrkt_id="+marketid );

              while(rs.next()){
                    result=rs.getInt(1);
              }
              rs.close();

              closeConnection();
        }catch(Exception e){
               System.out.println(e.getMessage());
                   
        }
        return result;


  }
    
 public void updateTable(String query,List<String> cond)
 {
	 	try {
			connectToDB();
		
			PreparedStatement ps=conn.prepareStatement(query);
			int i=1;
			String temp;
		
			while(i<=cond.size())
			{
				temp=cond.get(i-1);
				
				ps.setString(i, temp);
				i++;
			}
			ps.executeUpdate();
			conn.commit();
	 	} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 }
 public void insertTable(String query,List<String> cond)
 {
	 	try {
			connectToDB();
		
			PreparedStatement ps=conn.prepareStatement(query);
			int i=1;
			String temp;
		
			while(i<=cond.size())
			{
				temp=cond.get(i-1);
				System.out.println(temp);
				ps.setString(i, temp);
				i++;
			}
			ps.executeUpdate();
			conn.commit();
			System.out.println("Insert Successful");
	 	} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 }
 
 
 
}