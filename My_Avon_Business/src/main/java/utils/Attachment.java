package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class Attachment {
	
	
    public void createDoc(String testCaseName, ArrayList<String> screenshots) throws Exception {
    	try{
    		System.out.println("creating screenshot document....");
            XWPFDocument doc = new XWPFDocument();
            XWPFParagraph p = doc.createParagraph();
            XWPFRun xwpfRun = p.createRun();
            
            
            int size = screenshots.size();
            for(int i=0;i<size;i++)
            {
                String imgFile=screenshots.get(i);
                int format=XWPFDocument.PICTURE_TYPE_JPEG;
                xwpfRun.setText(imgFile);
                xwpfRun.addBreak();
                xwpfRun.addPicture (new FileInputStream(imgFile), format, imgFile, Units.toEMU(475), Units.toEMU(280)); // 200x200 pixels
             
            }
            
            
            FileOutputStream out = new FileOutputStream("./screenshots/"+testCaseName+".docx");
            doc.write(out);
            out.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	
    }
}