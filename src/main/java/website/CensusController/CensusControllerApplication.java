package website.CensusController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import website.JBDC.NationalAverages;

import java.io.File;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Controller
public class CensusControllerApplication {

	private static final Logger logger = LoggerFactory.getLogger(CensusControllerApplication.class);

	@Autowired
	private final CensusJDBCPost censusJDBCPost;
	private final NationalAverages nationalAverages;

	@Autowired
	public CensusControllerApplication(CensusJDBCPost censusJDBCPost, NationalAverages nationalAverages) {
		this.censusJDBCPost = censusJDBCPost;
		this.nationalAverages = nationalAverages;
		nationalAverages.setCensusControllerApplication(this);
	}

	@GetMapping("/dataForm")
	public String censusForm(Model model) {
		model.addAttribute("dataForm", new DataForm());
		return "dataForm";
	}


	@PostMapping("/dataForm")
	public String censusSubmit(@ModelAttribute DataForm dataForm, Model model) {
		logger.info("DataForm contents: {}", dataForm);

		censusJDBCPost.addCensusData(dataForm);

		model.addAttribute("dataForm", dataForm);
		return "submission";
	}

	long timestamp;
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp (long timestamp) {
		this.timestamp = timestamp;
	}

	@GetMapping("/chart")
	public String censusChart(@RequestParam String data, Model model) {
		Chart chart = new Chart();
		chart.setData(data);
		model.addAttribute("chart", chart);

		setTimestamp(System.currentTimeMillis() + new Random().nextLong());

		model.addAttribute("chart", chart);
		model.addAttribute("timestamp", timestamp);

		nationalAverages.getCensusData(chart);
//		return "chart";
		try {
			File f = new File("C:\\Users\\kmcel\\Documents\\Capstone_two\\CensusController\\src\\main\\resources\\static\\image_" + timestamp + ".jpg");
			boolean exist = f.exists();
			for (int i=0; i<10;) {
				if (!exist) {
					Thread.sleep(4000);
					i++;
				} else {
					i += 9;
					System.out.println("Found the correct image");
					return "chart";
				}
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return "dataForm";
	}

}
