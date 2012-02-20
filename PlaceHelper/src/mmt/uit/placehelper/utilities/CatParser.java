package mmt.uit.placehelper.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mmt.uit.placehelper.R;
import mmt.uit.placehelper.models.Child;
import mmt.uit.placehelper.models.MainGroup;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class CatParser {

	//XML TAG
	static final String CAT = "Category";
	static final String GROUP = "Group";
	static final String ID = "Id";
	static final String NAME ="Name";
	static final String IMAGE = "Image";	
	static final String CHILD = "Child";
	static final String TYPE = "Types";
		
	//Context
	Context mContext;
	
	public CatParser (Context ct){
		this.mContext = ct;
	}
	
	public List<MainGroup> parse(){
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		List<MainGroup> results = new ArrayList<MainGroup>();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(this.getInput());
			org.w3c.dom.Element root = dom.getDocumentElement();
			NodeList groups = root.getElementsByTagName(GROUP);
			for (int i=0;i<groups.getLength();i++){
				MainGroup current = new MainGroup();
				Node group = groups.item(i);
				NodeList properties = group.getChildNodes();
				for (int j=0;j<properties.getLength();j++){
					Node property = properties.item(j);
					String name = property.getNodeName();
					if (name.equalsIgnoreCase(ID)){
						current.setId(Integer.parseInt(property.getFirstChild().getNodeValue()));
					}
					else if (name.equalsIgnoreCase(NAME)){
						current.setName(mContext.getResources().getIdentifier("mmt.uit.placehelper:"+property.getFirstChild().getNodeValue(), null, null));
					}
					else if (name.equalsIgnoreCase(IMAGE)){
						current.setImgID(mContext.getResources().getIdentifier("mmt.uit.placehelper:"+property.getFirstChild().getNodeValue(), null, null));
					}
					else if(name.equalsIgnoreCase(TYPE)){
						current.setTypes(property.getFirstChild().getNodeValue());
					}
					else if (name.equalsIgnoreCase(CHILD)){
						NodeList childpros = property.getChildNodes();
						Child currentChild = new Child();
						for (int k=0;k<childpros.getLength();k++){
							Node childpro = childpros.item(k);
							String childname = childpro.getNodeName();
							
							if (childname.equalsIgnoreCase(ID)){
								currentChild.setId(Integer.parseInt(childpro.getFirstChild().getNodeValue()));
							}
							else if (childname.equalsIgnoreCase(NAME)){
								currentChild.setName(mContext.getResources().getIdentifier("mmt.uit.placehelper:"+childpro.getFirstChild().getNodeValue(), null, null));
							}
							else if (childname.equalsIgnoreCase(IMAGE)){
								currentChild.setImgID(mContext.getResources().getIdentifier("mmt.uit.placehelper:"+childpro.getFirstChild().getNodeValue(), null, null));
							}
							else if (childname.equalsIgnoreCase(TYPE)){
								currentChild.setTypes(childpro.getFirstChild().getNodeValue());
							}
						}
						current.addChild(currentChild);
					}
				}
				results.add(current);
			}
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}
		
		return results;
	}
	
	protected InputStream getInput() throws IOException{
		FileInputStream fl = null;
		File sdcard = Environment.getExternalStorageDirectory();
		File file = new File(sdcard, "categories.xml");
		if (!file.exists()){
			InputStream xml = mContext.getResources().openRawResource(R.raw.categories);
			OutputStream out = new FileOutputStream(file);
			// Transfer bytes from in to out
			byte[] buf = new byte[1024];
			int len;
			try {
			    // A little more explicit
			    while ( (len = xml.read(buf, 0, buf.length)) != -1){
			         out.write(buf, 0, len);
			    }
			} 			
			finally {
			    // Ensure the Streams are closed:
			    xml.close();
			    out.close();
			}
			
			file = new File(sdcard, "categories.xml");

		}
		try {
			fl = new FileInputStream(file);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fl;
		
	}
	
	private boolean	checkExternalMedia(){
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
		    // Something else is wrong. It may be one of many other states,but all we need
		    //  to know is we can neither read nor write
			Log.i("Stat","State="+state+" Not good");
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}

		Log.i("Stat","Available="+mExternalStorageAvailable+"Writeable="+mExternalStorageWriteable+" State"+state);
		return (mExternalStorageAvailable && mExternalStorageWriteable);
	}
}
