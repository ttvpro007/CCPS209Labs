import javax.swing.JSlider;

public class JSliderFactory implements IFactory<JSlider> {

	public static JSlider create(int min, int max) {
		
		return new JSliderFactory(min, max).create();
	}
	
	public JSliderFactory(int min, int max) {
		
		this.min = min;
		this.max = max;
	}
	
	@Override
	public JSlider create() {

		var slider = new JSlider();
		
		slider.setMinimum(min);
		slider.setMaximum(max);
		
		return slider;
	}

	private int min;
	private int max;
}
