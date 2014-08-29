package fjab.worldcup.scripts

class Example{
	
	
	static void run(){
		
		try {			
		   String content = "hello world";

		   File file = new File("/tmp/fjab.txt");

		   // if file doesnt exists, then create it
		   if (!file.exists()) {
			   file.createNewFile();
		   }

		   FileWriter fw = new FileWriter(file.getAbsoluteFile());
		   BufferedWriter bw = new BufferedWriter(fw);
		   bw.write(content);
		   bw.close();

		   System.out.println("Done");

	   } catch (IOException e) {
		   e.printStackTrace();
	   }
		
		
	}
}
