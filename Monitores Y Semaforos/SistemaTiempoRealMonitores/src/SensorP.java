
import java.util.Random;


public class SensorP extends Thread{
	
	public SensorP () {
		
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
				main.P.Set(media,true);
			} else {
				main.P.Set(media, false);
			}
		}
	}
}
