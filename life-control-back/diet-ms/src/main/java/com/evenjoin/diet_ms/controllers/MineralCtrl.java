package com.evenjoin.diet_ms.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.evenjoin.diet_ms.entity.Mineral;
import com.evenjoin.diet_ms.services.MineralSvc;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class MineralCtrl {

	private static final Logger logger = LoggerFactory.getLogger(MineralCtrl.class);

	@Autowired
	private MineralSvc mineralSvc;

	// Get all minerals
	@CircuitBreaker(name = "mineralBreaker", fallbackMethod = "getListObjectCB")
	@GetMapping("/mineral/all")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Mineral>> getCategories() {
		return CompletableFuture.supplyAsync(() -> mineralSvc.getMinerals());
	}

	// Get a mineral
	@CircuitBreaker(name = "mineralBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "mineralBreaker")
	@GetMapping("/mineral/{idMineral}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Mineral> getMineral(@PathVariable Long idMineral) {
		return CompletableFuture.supplyAsync(() -> mineralSvc.getMineral(idMineral));
	}

	// Add a mineral
	@CircuitBreaker(name = "mineralBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "mineralBreaker")
	@PostMapping("/mineral/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CompletableFuture<Mineral> addMineral(@RequestBody Mineral mineral) {
		return CompletableFuture.supplyAsync(() -> mineralSvc.addMineral(mineral));
	}

	// Update a mineral
	@CircuitBreaker(name = "mineralBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "mineralBreaker")
	@PutMapping("/mineral/update/{idMineral}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Mineral> updateMineral(@RequestBody Mineral mineral, @PathVariable Long idMineral) {
		return CompletableFuture.supplyAsync(() -> {
			Mineral currentMineral = mineralSvc.getMineral(idMineral);
			currentMineral.setCalcium(mineral.getCalcium());
			currentMineral.setPhosphorus(mineral.getPhosphorus());
			currentMineral.setSodium(mineral.getSodium());
			currentMineral.setChloride(mineral.getChloride());
			currentMineral.setMagnesium(mineral.getMagnesium());
			currentMineral.setSulfur(mineral.getSulfur());
			currentMineral.setIron(mineral.getIron());
			currentMineral.setZinc(mineral.getZinc());
			currentMineral.setCopper(mineral.getCopper());
			currentMineral.setManganese(mineral.getManganese());
			currentMineral.setIodine(mineral.getIodine());
			currentMineral.setSelenium(mineral.getSelenium());
			currentMineral.setMolybdenum(mineral.getMolybdenum());
			currentMineral.setCobalt(mineral.getCobalt());
			currentMineral.setFluoride(mineral.getFluoride());
			currentMineral.setChromium(mineral.getChromium());
			return mineralSvc.addMineral(currentMineral);
		});
	}

	// Delete a mineral
	@CircuitBreaker(name = "mineralBreaker", fallbackMethod = "getVoidCB")
	@TimeLimiter(name = "mineralBreaker")
	@DeleteMapping("/mineral/{idMineral}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public CompletableFuture<Void> deleteMineral(@PathVariable Long idMineral) {
		return CompletableFuture.runAsync(() -> mineralSvc.deleteMineral(idMineral));
	}

	// Get minerals by ingredient
	@CircuitBreaker(name = "mineralBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "mineralBreaker")
	@GetMapping("/mineral/ingredient/{idIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Mineral> getMineralsByIngredient(@PathVariable Long idIngredient) {
		return CompletableFuture.supplyAsync(() -> mineralSvc.getMineralsByIngredient(idIngredient));
	}

	// Get minerals by recipe
	@CircuitBreaker(name = "mineralBreaker", fallbackMethod = "getMapObjectCB")
	@TimeLimiter(name = "mineralBreaker")
	@GetMapping("/mineral/recipe/{idRecipe}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, Object>> getMineralsByRecipe(@PathVariable Long idRecipe) {
		return CompletableFuture.supplyAsync(() -> {
			Object response = mineralSvc.getMineralsByRecipe(idRecipe);
			Object[] res = (Object[]) response;
			Map<String, Object> jsonObject = new HashMap<String, Object>();
			jsonObject.put("calcium", res[0]);
			jsonObject.put("phosphorus", res[1]);
			jsonObject.put("potassium", res[2]);
			jsonObject.put("sodium", res[3]);
			jsonObject.put("chloride", res[4]);
			jsonObject.put("magnesium", res[5]);
			jsonObject.put("sulfur", res[6]);
			jsonObject.put("iron", res[7]);
			jsonObject.put("zinc", res[8]);
			jsonObject.put("copper", res[9]);
			jsonObject.put("manganese", res[10]);
			jsonObject.put("iodine", res[11]);
			jsonObject.put("selenium", res[12]);
			jsonObject.put("molybdenum", res[13]);
			jsonObject.put("cobalt", res[14]);
			jsonObject.put("fluoride", res[15]);
			jsonObject.put("chromium", res[16]);
			return jsonObject;
		});
	}

	// Get minerals by diet
	@CircuitBreaker(name = "mineralBreaker", fallbackMethod = "getMapObjectCB")
	@TimeLimiter(name = "mineralBreaker")
	@GetMapping("/mineral/diet/{idDiet}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, Object>> getMineralsByDiet(@PathVariable Long idDiet) {
		return CompletableFuture.supplyAsync(() -> {
			Object response = mineralSvc.getMineralsByDiet(idDiet);
			Object[] res = (Object[]) response;
			Map<String, Object> jsonObject = new HashMap<String, Object>();
			jsonObject.put("calcium", res[0]);
			jsonObject.put("phosphorus", res[1]);
			jsonObject.put("potassium", res[2]);
			jsonObject.put("sodium", res[3]);
			jsonObject.put("chloride", res[4]);
			jsonObject.put("magnesium", res[5]);
			jsonObject.put("sulfur", res[6]);
			jsonObject.put("iron", res[7]);
			jsonObject.put("zinc", res[8]);
			jsonObject.put("copper", res[9]);
			jsonObject.put("manganese", res[10]);
			jsonObject.put("iodine", res[11]);
			jsonObject.put("selenium", res[12]);
			jsonObject.put("molybdenum", res[13]);
			jsonObject.put("cobalt", res[14]);
			jsonObject.put("fluoride", res[15]);
			jsonObject.put("chromium", res[16]);
			return jsonObject;
		});
	}

	// Get minerals by diet between dates
	@CircuitBreaker(name = "mineralBreaker", fallbackMethod = "getMapObjectCB")
	@TimeLimiter(name = "mineralBreaker")
	@GetMapping("/mineral/diet/{startDate}/{endDate}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Map<String, Object>>> getMineralsByDietRange(@PathVariable String startDate,
			@PathVariable String endDate) {
		return CompletableFuture.supplyAsync(() -> {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate1;
			Date endDate1;
			try {
				startDate1 = formatter.parse(startDate);
				endDate1 = formatter.parse(endDate);
			} catch (ParseException e) {
				throw new RuntimeException("Invalid date: " + e.getMessage());
			}
			List<Object> response = mineralSvc.getMineralsByDietRange(startDate1, endDate1);
			List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
			for (Object res : response) {
				Object[] row = (Object[]) res;
				Map<String, Object> jsonObject = new HashMap<String, Object>();
				jsonObject.put("idDiet", row[0]);
				jsonObject.put("date", row[1]);
				jsonObject.put("calcium", row[2]);
				jsonObject.put("phosphorus", row[3]);
				jsonObject.put("potassium", row[4]);
				jsonObject.put("sodium", row[5]);
				jsonObject.put("chloride", row[6]);
				jsonObject.put("magnesium", row[7]);
				jsonObject.put("sulfur", row[8]);
				jsonObject.put("iron", row[9]);
				jsonObject.put("zinc", row[10]);
				jsonObject.put("copper", row[11]);
				jsonObject.put("manganese", row[12]);
				jsonObject.put("iodine", row[13]);
				jsonObject.put("selenium", row[14]);
				jsonObject.put("molybdenum", row[15]);
				jsonObject.put("cobalt", row[16]);
				jsonObject.put("fluoride", row[17]);
				jsonObject.put("chromium", row[18]);
				json.add(jsonObject);
			}
			return json;
		});
	}

	// (CircuitBreaker) Get void circuit breaker
	public CompletableFuture<Void> getVoidCB(Throwable t) {
		return CompletableFuture.runAsync(() -> logger.error("Error while deleting " + t));
	}

	// (CircuitBreaker) Get object circuit breaker
	public CompletableFuture<Mineral> getObjectCB(Throwable t) {
		logger.error("Enabled mineral breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Mineral mineral = new Mineral();
			mineral.setIdMineral(null);
			mineral.setCalcium(null);
			mineral.setPhosphorus(null);
			mineral.setSodium(null);
			mineral.setChloride(null);
			mineral.setMagnesium(null);
			mineral.setSulfur(null);
			mineral.setIron(null);
			mineral.setZinc(null);
			mineral.setCopper(null);
			mineral.setManganese(null);
			mineral.setIodine(null);
			mineral.setSelenium(null);
			mineral.setMolybdenum(null);
			mineral.setCobalt(null);
			mineral.setFluoride(null);
			mineral.setChromium(null);
			return mineral;
		});
	}

	// (CircuitBreaker) Get list object circuit breaker
	public CompletableFuture<List<Mineral>> getListObjectCB(Throwable t) {
		logger.error("Enabled mineral breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Mineral mineral = new Mineral();
			List<Mineral> list = new ArrayList<Mineral>();
			mineral.setIdMineral(null);
			mineral.setCalcium(null);
			mineral.setPhosphorus(null);
			mineral.setSodium(null);
			mineral.setChloride(null);
			mineral.setMagnesium(null);
			mineral.setSulfur(null);
			mineral.setIron(null);
			mineral.setZinc(null);
			mineral.setCopper(null);
			mineral.setManganese(null);
			mineral.setIodine(null);
			mineral.setSelenium(null);
			mineral.setMolybdenum(null);
			mineral.setCobalt(null);
			mineral.setFluoride(null);
			mineral.setChromium(null);
			list.add(mineral);
			return list;
		});
	}

	// (CircuitBreaker) Get map object circuit breaker
	public CompletableFuture<Map<String, Object>> getMapObjectCB(Throwable t) {
		logger.error("Enabled mineral breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Object> jsonObject = new HashMap<String, Object>();
			jsonObject.put("calcium", null);
			jsonObject.put("phosphorus", null);
			jsonObject.put("potassium", null);
			jsonObject.put("sodium", null);
			jsonObject.put("chloride", null);
			jsonObject.put("magnesium", null);
			jsonObject.put("sulfur", null);
			jsonObject.put("iron", null);
			jsonObject.put("zinc", null);
			jsonObject.put("copper", null);
			jsonObject.put("manganese", null);
			jsonObject.put("iodine", null);
			jsonObject.put("selenium", null);
			jsonObject.put("molybdenum", null);
			jsonObject.put("cobalt", null);
			jsonObject.put("fluoride", null);
			jsonObject.put("chromium", null);
			return jsonObject;
		});
	}

}
