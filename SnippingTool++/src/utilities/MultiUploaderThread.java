package utilities;

import java.io.File;

public class MultiUploaderThread extends Thread
{
	Thread thread;
	MultiUploader mu;
	String type;
	Upload upload = new Upload();
	File file;

	public MultiUploaderThread(File file, String type, MultiUploader mu)
	{
		this.mu = mu;
		this.type = type;
		this.file = file;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run()
	{
		String response = upload.uploadImageFile(file, type);
		if (response != null)
		{
			String upLink[] = response.split("<large_thumbnail>");
			upLink = upLink[1].split("l.jpg</large_thumbnail>");
			upLink[0] += ".png";
			String delLink[] = response.split("<delete_page>");
			delLink = delLink[1].split("</delete_page>");
			mu.addLink(upLink[0]);
			mu.addDeletionLink(delLink[0]);
		} else
			mu.addLink("Unknown Error returning link");
	}
}
