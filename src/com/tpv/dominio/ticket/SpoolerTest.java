/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpv.dominio.ticket;

import java.math.BigDecimal;
import org.luque.print.fiscal.FiscalPacket;
import org.luque.print.fiscal.comm.SpoolerTCPComm;
import org.luque.print.fiscal.exception.FiscalPrinterIOException;
import org.luque.print.fiscal.exception.FiscalPrinterStatusError;
import org.luque.print.fiscal.hasar.HasarCommands;
import org.luque.print.fiscal.hasar.HasarFiscalPacket;
import org.luque.print.fiscal.hasar.HasarFiscalPrinter;
import org.luque.print.fiscal.hasar.HasarPrinterP320F;
import org.luque.print.fiscal.hasar.HasarPrinterP715F;
import org.luque.print.fiscal.msg.FiscalMessages;
//78.95
/**
 *
 * @author daniel
 */
public class SpoolerTest {
    
    
    public void printDnf(){
            SpoolerTCPComm stcp = new SpoolerTCPComm("127.0.0.1",1600);
            FiscalPacket request;
            HasarFiscalPrinter hfp = new HasarPrinterP320F(); //HasarPrinterP715F();
            FiscalPacket response = new HasarFiscalPacket("ISO8859_1", 2015, hfp);

            //System.out.println(request.encodeString());

            
            
            try{
                stcp.connect();
                request = hfp.cmdOpenNonFiscalReceipt();
                stcp.execute(request, response);
                System.out.println("RESPUESTA DE LINEA DE ITEM: "+response.encodeString());
                
                request = hfp.cmdPrintNonFiscalText("IMPRESOR DE DOCUMENTO NO FISCAL", null);
                System.out.println("CODIGO DE IMPRESION: "+request.encodeString());    
                stcp.execute(request, response);
                System.out.println("RESPUESTA DE LINEA DE ITEM: "+response.encodeString());
                
                
                request = hfp.cmdCloseNonFiscalReceipt(new Integer(1));
                stcp.execute(request, response);
                System.out.println("RESPUESTA DE LINEA DE ITEM: "+response.encodeString());
                
                stcp.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        
        
    }
    
    public void cancelarDocument(){
            SpoolerTCPComm stcp = new SpoolerTCPComm("127.0.0.1",1600);
            FiscalPacket request;
            HasarFiscalPrinter hfp = new HasarPrinterP320F();
            FiscalPacket response = new HasarFiscalPacket("ISO8859_1", 2015, hfp);
        
            try{
                stcp.connect();
                request = hfp.cmdCancelDocument();
                stcp.execute(request, response);
                System.out.println("RESPUESTA DE LINEA DE ITEM: "+response.encodeString());
                
                stcp.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        
    }
    
    public void printDf(){
            SpoolerTCPComm stcp = new SpoolerTCPComm("127.0.0.1",1600);
            FiscalPacket request;
            HasarFiscalPrinter hfp = new HasarPrinterP320F();
            FiscalPacket response = new HasarFiscalPacket("ISO8859_1", 2015, hfp);
                    
            //System.out.println(request.encodeString());

            
            
            try{
                stcp.connect();
                request = hfp.cmdOpenFiscalReceipt("C");
                stcp.execute(request, response);
                System.out.println("RESPUESTA APERTURA DE DOCUMENTO FISCAL: "+response.encodeString());

                /*request = hfp.cmdPrintLineItem("CACAO", new BigDecimal(1), new BigDecimal(1), new BigDecimal(21), false, new BigDecimal(1), true,null);
                stcp.execute(request, response);
                System.out.println("RESPUESTA DE LINEA DE ITEM: "+response.encodeString());
                */
                request = hfp.cmdCloseFiscalReceipt(new Integer(1));
                stcp.execute(request, response);
                System.out.println("CIERRE DE DOCUMENTO FISCAL: "+response.encodeString());
                
                stcp.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        
    }
            
    
    public void printDF2(){
          SpoolerTCPComm stcp = new SpoolerTCPComm("127.0.0.1",1600);
          HasarFiscalPrinter hfp = new HasarPrinterP320F(stcp);
          FiscalPacket request;
          FiscalPacket response;
          try{
              hfp.connect();
              request = hfp.cmdOpenNonFiscalReceipt();
              response = hfp.execute(request);
              FiscalMessages fMsg = hfp.getMessages();
              System.out.println(fMsg.getErrorsAsString());

              request = hfp.cmdPrintNonFiscalText("DOCUMENTO NO FISCAL", null);
              response = hfp.execute(request);
              fMsg = hfp.getMessages();
              System.out.println(fMsg.getErrorsAsString());
              
              request = hfp.cmdCloseNonFiscalReceipt(null);
              response = hfp.execute(request);
              fMsg = hfp.getMessages();
              System.out.println(fMsg.getErrorsAsString());
          
          }catch(FiscalPrinterStatusError e)    {
              System.out.println(e.getFullMessage());
              
          }catch(FiscalPrinterIOException e){
              System.out.println(e.getFullMessage());
              //hfp.get
          }catch(Exception e){
              e.printStackTrace();
          }
      
    }
    
    
    public static void main(String args[]){
        SpoolerTest st = new SpoolerTest();
        //st.printDF2();
        st.cancelarDocument();
    }
    
}
