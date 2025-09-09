package BrowserUtility;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtil {
	
	private  WebDriver driver;
	// Don’t make driver public — anyone can access it and it might stay null.
	// This driver is only for use inside this ElementUtil class.

	
	public ElementUtil(WebDriver driver) {
		this.driver=driver;
	}

	public void clickElement(By Locator) {
		getElement(Locator).click();
	}
	
	public void sendKeysToElement(By locator, String Value) {
		getElement(locator).sendKeys(Value);;
	}
	
	public WebElement getElement(By locator) {
		return driver.findElement(locator); 
	}
	public  List<WebElement> getElements(By locator) {
		 return driver.findElements(locator);
	}
	
	// Returns a list of non-empty text values from all elements found using the given locator.
		public ArrayList<String> getTextFromElements(By locator) {
			List<WebElement> eleList = getElements(locator);
			//made blank array list, auto adjusts as values add or remove
			 ArrayList<String> eletextList = new ArrayList<String>();
			
				for (WebElement e : eleList) {
					String text = e.getText();
						if(text.length()!=0) {  // 
							eletextList.add(text);
						}		
				}
				return eletextList;
		}
		
		// Returns a list of attribute values for all elements found using the given locator.
		public  ArrayList<String> getAttributesFromElements(By locator, String attrName) {
			List<WebElement> eleist = getElements(locator);
			ArrayList<String> eleattrList = new ArrayList<String>();
			
			for (WebElement e : eleist) {
				String attrValue = e.getDomAttribute(attrName);
				eleattrList.add(attrValue);
			}
			return eleattrList;
		}
		
		// Returns a list of text from all footer links found using the given locator.	
		public ArrayList<String> getFooterLinkTexts(By locator) {
			List<WebElement> footerLinks  = getElements(locator);		
				ArrayList<String> footerLinkTexts  = new ArrayList<String>();
				for(WebElement e : footerLinks) {
					String linkText  = e.getText();
					footerLinkTexts.add(linkText);  // collect text into the list
				}
				return footerLinkTexts; 
		}
		
		//Finds elements by locator, clicks the one matching valueToClick if found,
		public  ArrayList<String> checkIfElementExists(By locator, String valueToClick) {
		    List<WebElement> optionsList = driver.findElements(locator);
		    ArrayList<String> ele = new ArrayList<>();
		    boolean found = false;

		    for (WebElement e : optionsList) {
		        String text = e.getText();
		        ele.add(text);

		        if (text.equals(valueToClick)) {
		            e.click();  // click the matching element
		            System.out.println(valueToClick + " is clicked");
		            found = true;
		            break; // exit loop immediately after clicking
		        }
		    }
		    if (!found) {
		        System.out.println(valueToClick + " not found");
		    }
		    return ele;
		}
	
		// Returns the count of elements matching the given locator.
		public  int getElementCount(By locator) {
			return getElements(locator).size();
		}
		
	//Types text, finds suggestions, clicks matching suggestion if found.
		
		/**
		 *  By searchField,         // 1. Locator for the input box
    		By suggestionLocator,   // 2. Locator for the list of suggestions
    		String searchText,      // 3. Text to type into the input box
    		String suggestionToClick // 4. Suggestion to click from the list 
		 **/
		
		public void searchAndClickSuggestion(By searchField, By suggestionLocator, String searchkey, String suggestionToClick) {
		    // Type the searchText into the search field
		    driver.findElement(searchField).sendKeys(searchkey);
		    
		    // Get all suggestions from the suggestionLocator
		    List<WebElement> suggestions = getElements(suggestionLocator);
		    
		    // Loop through each suggestion
		    for (WebElement e : suggestions) {
		        String suggestionText = e.getText();
		        
		        // Click the suggestion if it matches suggestionToClick
		        if (suggestionText.contains(suggestionToClick)) {
		            e.click();
		            System.out.println("Clicked on: " + suggestionText);
		            break;
		        }
		    }
		}
		
		/**********Select Classes**********/
		
		public  WebElement fetchElement(By locator) {
			return driver.findElement(locator);
		}
		public void selectByIndex(By locator, int index) {
			Select select = new Select(fetchElement(locator));
			select.selectByIndex(index);
		}
		public void selectByVisibleText(By locator, String visiableText) {
			Select select = new Select(fetchElement(locator));
			select.selectByVisibleText(visiableText);
		}
		public  void selectByValue(By locator, String Value) {
			Select select = new Select(fetchElement(locator));
			select.selectByValue(Value);
		}
		
		// Selects multiple/ more then one selection dropdown options; use "all" to select everything.
		public void selectMultipleOptions(By locator, String... values) {
		    Select select = new Select(getElement(locator));

		    if (isDropdownMultple(locator)) {
		        if (values[0].equalsIgnoreCase("all")) {
		            List<WebElement> options = select.getOptions();
		            for (WebElement option : options) {
		                select.selectByVisibleText(option.getText());
		            }
		        } else {
		            for (String val : values) {
		                select.selectByVisibleText(val);
		            }
		        }
		    }
		}
		// Checks if the dropdown allows multiple selections.
		public boolean isDropdownMultple(By locator) {
			Select select = new Select(getElement(locator)); 
			return select.isMultiple() ? true : false; 
		}
		
		// To check the DropdownoptionsCount size 
		public int getDropdownoptionsCount(By locator) {
			Select select = new Select(fetchElement(locator));
			return select.getOptions().size();
		}
		
		// Returns all visible option texts from a dropdown as a list.
		public  List<String> getDropdopwOptions(By locator) {
			Select select = new Select(fetchElement(locator));
				List<WebElement> optionList = select.getOptions();
					List<String> optionTextList = new ArrayList<String>();
					
						for(WebElement e : optionList) {
							String alltext=e.getText();
							optionTextList.add(alltext);
							}
						return optionTextList;
					}
		
		// Selects dropdown option without using Selenium’s Select class.
		public  void selectDropDownValue(By locator, String value) {
			List<WebElement> optionsList = driver.findElements(locator);
			for (WebElement e : optionsList) {
				String text = e.getText();
					if(text.equals(value)) {
						System.out.println("here");
						}
					}
			}

		/**********Alert<i>**********/
		// Handles JavaScript alert pop-up, Clicks element->alert, logs alert text, then accepts it.
		public  void clickElementAndAcceptAlert(By alertTriggerLocator) {
			driver.findElement(alertTriggerLocator).click();
			Alert alert = driver.switchTo().alert();
		    String alertText  = alert.getText();
		    System.out.println("Alert Text: " + alertText );
		    alert.accept();    
		}
		
		// Clicks element to open confirm alert, prints text, accepts or dismisses based on flag.
		public void clickElementAndHandleConfirm(By Jsconfirm, boolean accept) {
			driver.findElement(Jsconfirm).click();
			Alert alert = driver.switchTo().alert();
		    String alertText  = alert.getText();
		    System.out.println("Alert Text: " + alertText );
		    
		    if (accept) {
		        alert.accept();
		    } else {
		        alert.dismiss();
		    }
		}
		/***************Wait***************/
		
		/**
		 * An expectation for checking that an element is present on the DOM of a page. 
		 * This does notnecessarily mean that the element is visible.
		 * @param locator
		 * @param timeOut
		 * @return
		 */
			public  WebElement waitForPresenceElement(By locator, int timeOut) {
				WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
				return wait.until(ExpectedConditions.presenceOfElementLocated(locator));	
				}
		/**
		* An expectation for checking that an element is present on the DOM of a page and visible.Visibility 
		* means that the element is not only displayed but also has a height and width that isgreater than 0
		* @param locator
		* @param timeOut
		* @return
		*/
			public  WebElement waitForVisiblityElement(By locator, int timeOut) {
				WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
				return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));	
					}
				
			public void doClick(By locator, int timeOut) {
				waitForVisiblityElement(locator, timeOut).click();
			}
			public void doSendKeys(By locator, String value, int timeOut) {
				waitForVisiblityElement(locator, timeOut).sendKeys(value);
				}
			/**
			* An expectation for checking that an element is present on the DOM of a page and visible.Visibility 
			* means that the element is not only displayed but also has a height and width that isgreater than 0
			* @param locator
			* @param timeOut
			* @param intervaltimeOut
			* @return
			*/
		public  WebElement waitForVisiblityElement(By locator, int timeOut, int intervaltimeOut) {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut), Duration.ofSeconds(intervaltimeOut));
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));	
		}
	
		 //Waits for the exact page title, returns it if found, else nul
	    public  String waitForExcatTitle(String excattitle, int timeout) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

	        try {
	            if (wait.until(ExpectedConditions.titleContains(excattitle))) {
	                return driver.getTitle(); // return the actual title once condition is met
	            }
	        } catch (TimeoutException e) {
	            System.out.println("Title does not contain: " + excattitle);
	        }

	        return null;
	    }
	    
	   /** 
	    * page’s title doesn’t clearly match what the user expects or clicks 
	    That method  waits until the page title contains the given text within the timeout and then returns the title, 
	    else prints a message and returns nul
	    */
	    public String waitForTitleContains(String titlefriction, int timeout) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

	        try {
	            if (wait.until(ExpectedConditions.titleContains(titlefriction))) {
	                return driver.getTitle(); // return the actual title once condition is met
	            }
	        } catch (TimeoutException e) {
	            System.out.println("Title does not contain: " + titlefriction);
	        }

	        return null;
	    }
		
	    //url friction 
	    public  String waitForURLContains(String urlfriction, int timeout) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

	        try {
	            if (wait.until(ExpectedConditions.titleContains(urlfriction))) {
	                return driver.getCurrentUrl(); // return the actual URL once condition is met
	            }
	        } catch (TimeoutException e) {
	            System.out.println("URL does not contain: " + urlfriction);
	        }

	        return null;
	    }
	    
	    // Excat URLTOBe
	    public  String waitForURLTOBE(String url, int timeout) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

	        try {
	            if (wait.until(ExpectedConditions.titleContains(url))) {
	                return driver.getCurrentUrl(); // return the actual URL once condition is met
	            }
	        } catch (TimeoutException e) {
	            System.out.println("URL  does not contain: " + url);
	        }

	        return null;
	    }
	    
	    
	    /**********JSAlERTSWAITS*********/
	    public Alert waitForJSAlert(int timeOUt) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        return wait.until(ExpectedConditions.alertIsPresent());
	    }
	    
	    public void acceotForJSAlert(int timeOUt) {
	    	waitForJSAlert(timeOUt).accept();	
	    }
	    public void dismissForJSAlert(int timeOUt) {
	    	waitForJSAlert(timeOUt).dismiss();
	    }
	    public String getJSAlertText(int timeOUt) {
	    	return waitForJSAlert(timeOUt).getText();
	    }
	    public void enterValueJSAlert(int timeOUt, String value) {
	    	 waitForJSAlert(timeOUt).sendKeys(value);
	    }
	    
	    /**********framesWAITS*********/
	    /**This method waits until the specified frame is available within the given timeout and then switches 
		   * the WebDriver’s focus into that frame.
		   * @param frameLocator
		   * @param timeOut
		   */
		  public  void waitForFrameLocator(By frameLocator, int timeOut) {
			  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
		  }
		  /**This method waits until the element located by the given locator is visible on the page within the timeout 
		   * and then returns that WebElement.
		   * @param locator
		   * @param timeOUt
		   * @return
		   */
		  public WebElement waitForVisiablyOfElemenr(By locator, int timeOUt) {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		    }
		//This method waits until the frame at the given index is available
		  public  void waitForFrameIndex(int frameindex, int timeOut) {
			  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameindex));
		  }
		  //This method waits until the frame with the given id or name is available
		  public  void waitForFrameIdOrName(String  IdOrName, int timeOut) {
			  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(IdOrName));
		  }
		  //This method waits until the given frame WebElement is available
		  public  void waitForFrameByElement(WebElement  frameElement, int timeOut) {
			  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
		  }
		  
	    
		/**********Action Classes**********/
			public void doActionsSendKeys(By locator, String value) {
				Actions act = new Actions(driver);
				act.sendKeys(getElement(locator), value).perform();
			}
			
			public void doActionsClick(By locator) {
				Actions act = new Actions(driver);
				act.click(getElement(locator)).perform();
			}
			
			public void twoLevelMenuHandle(By parentMenuLocator, By childMenuLocator) throws InterruptedException {
				Actions act = new Actions(driver);
				act.moveToElement(getElement(parentMenuLocator)).build().perform();
				Thread.sleep(1000);
				doClick(childMenuLocator);
			}
			
			public void fourLevelMenuHandle(By parentMenuLocator, By firstChildMenuLocaor, By secondChildMenuLocaor,
				By thirdChildMenuLocaor) throws InterruptedException {

				Actions act = new Actions(driver);

				doClick(parentMenuLocator);
				Thread.sleep(1000);

				act.moveToElement(getElement(firstChildMenuLocaor)).build().perform();

				Thread.sleep(1000);

				act.moveToElement(getElement(secondChildMenuLocaor)).build().perform();

				Thread.sleep(1000);

				doClick(thirdChildMenuLocaor);
			}		
			public  void doClick(By locator) {
				getElement1(locator).click();
			}
				
			public WebElement getElement1(By locator) {
				return driver.findElement(locator);
			}
			
}
