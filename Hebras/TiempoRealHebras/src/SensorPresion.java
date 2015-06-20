import java.util.Random;

public class SensorPresion extends Thread {

	public SensorPresion() {

	}

	public void run() {

		Random alea = new Random();

		while (true) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			float media = 0;
			for (int i = 0; i < 1000; i++) {
				media += alea.nextFloat() * 80;
			}

			media = media / 1000;
			if (media > 40) {
				main.P.Set(media, true);
			} else {
				main.P.Set(media, false);
			}
		}
	}
}
