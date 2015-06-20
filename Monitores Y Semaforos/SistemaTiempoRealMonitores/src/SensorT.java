import java.util.Random;


public class SensorT extends Thread{
	
	public SensorT () {
		
	}

	public void run () {
		
		Random val = new Random();
		
		while (true) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			float media=0;
			for (int i=0;i<1000;i++) {
				media+=val.nextFloat()*80;
			}

			media=media/1000;
			if (media>40) {
				main.T.Set(media,true);
			} else {
				main.T.Set(media, false);
			}
		}
	}
}
