/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpv.dominio.ticket;

import java.math.BigDecimal;
import org.openXpertya.print.fiscal.FiscalPacket;
import org.openXpertya.print.fiscal.comm.SpoolerTCPComm;
import org.openXpertya.print.fiscal.hasar.HasarCommands;
import org.openXpertya.print.fiscal.hasar.HasarFiscalPacket;
import org.openXpertya.print.fiscal.hasar.HasarFiscalPrinter;
import org.openXpertya.print.fiscal.hasar.HasarPrinterP320F;
import org.openXpertya.print.fiscal.hasar.HasarPrinterP715F;
//78.95
/**
 *
 * @author daniel
 */
public class SpoolerTest {
    
    
    public void printDnf(){
            SpoolerTCPComm stcp = new SpoolerTCPComm("134.14.1.3",1600);
            FiscalPacket request;
            HasarFiscalPrinter hfp = new HasarPrinterP715F();
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
    
    public void printDf(){
            SpoolerTCPComm stcp = new SpoolerTCPComm("134.14.1.3",1600);
            FiscalPacket request;
            HasarFiscalPrinter hfp = new HasarPrinterP715F();//HasarPrinterP320F();
            FiscalPacket response = new HasarFiscalPacket("ISO8859_1", 2015, hfp);

            //System.out.println(request.encodeString());

            
            
            try{
                stcp.connect();
                request = hfp.cmdOpenFiscalReceipt("C");
                stcp.execute(request, response);
                
                //cmdPrintLineItem(String description, BigDecimal quantity
                //, BigDecimal price, BigDecimal ivaPercent, boolean substract
                //, BigDecimal internalTaxes, boolean basePrice, Integer display) {                
                request = hfp.cmdPrintLineItem("CACAO", new BigDecimal(1), new BigDecimal(1), new BigDecimal(21), false, new BigDecimal(1), true,null);
                stcp.execute(request, response);
                System.out.println("RESPUESTA DE LINEA DE ITEM: "+response.encodeString());
                request = hfp.cmdCloseFiscalReceipt(new Integer(1));
                stcp.execute(request, response);
                
                stcp.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        
    }
            
    
    
    public static void main(String args[]){
        SpoolerTest st = new SpoolerTest();
        st.printDf();
        //st.printDnf();
    }
    
}
