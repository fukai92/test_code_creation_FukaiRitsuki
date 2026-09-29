package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		//トップページへアクセス
		goTo("http://localhost:8080/lms/");
												
		//タイトルエビデンス取得
		String evidenceTitle = "ログイン | LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());
											
		//ボタン表示エビデンス取得
		boolean evidenceButton = webDriver.findElement(By.cssSelector("input[type='submit']")).isDisplayed();
		assertTrue(evidenceButton);
												
		//エビデンス証跡取得
		getEvidence(new Object(){}, "テスト01");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		//DBにあるIDとパスワードを入力
        webDriver.findElement(By.name("loginId")).sendKeys("StudentAA03");
        webDriver.findElement(By.name("password")).sendKeys("StudentAA03");
        
        //ログインボタンをクリック
        webDriver.findElement(By.cssSelector("input[type='submit']")).click();
        
        //コース詳細にある詳細ボタン表示までの時間確保
        visibilityTimeout(By.cssSelector("input[value='詳細']"), 5);
        
        //タイトルエビデンス取得
      	String evidenceTitle = "コース詳細 | LMS";
      	assertEquals(evidenceTitle, webDriver.getTitle());
      	
      	//各セクション詳細ボタン表示エビデンス取得
      	boolean evidenceButton = webDriver.findElement(By.cssSelector("input[value='詳細']")).isDisplayed();
      	assertTrue(evidenceButton);
      	
        //エビデンス証跡取得
        getEvidence(new Object(){}, "テスト02");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		//ヘッダーにある「ようこそ受講生AA3さん」をクリック
		webDriver.findElement(By.partialLinkText("ようこそ")).click();
								
		//ユーザー詳細画面にある「パスワード変更する」ボタン表示までの時間確保
		visibilityTimeout(By.cssSelector("input[value='パスワード変更する']"), 5);
								
		//「パスワード変更する」ボタン表示エビデンス取得
		boolean evidenceButton = webDriver.findElement(By.cssSelector("input[value='パスワード変更する']")).isDisplayed();
		assertTrue(evidenceButton);
								
		//タイトルエビデンス取得
		String evidenceTitle = "ユーザー詳細";
		assertEquals(evidenceTitle, webDriver.getTitle());
								
		//エビデンス証跡取得
		getEvidence(new Object(){}, "テスト03");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//「修正する」ボタンを表示するためスクロール
		scrollBy("200");
				
		//「修正する」ボタンをクリック
		webDriver.findElement(By.cssSelector("input[value='修正する']")).click();
		
		//エビデンス証跡取得
		getEvidence(new Object(){}, "テスト04");
		
		//「提出する」ボタンが下部にあるためスクロール
		scrollBy("500");
		
		//レポート登録画面にある「提出する」ボタン表示までの時間確保
		visibilityTimeout(By.cssSelector("button[type='submit']"), 5);
				
		//タイトルエビデンス取得
		String evidenceTitle = "レポート登録 | LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());
				
		//「提出する」ボタン表示エビデンス取得
		boolean evidenceButton = webDriver.findElement(By.cssSelector("button[type='submit']")).isDisplayed();
		assertTrue(evidenceButton);
				
		
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		//理解度に3と入力し学習項目には何も記述しない
		WebElement intFieldValue = webDriver.findElement(By.id("intFieldValue_0"));
	    intFieldValue.sendKeys("3"); 
	    
	    //「提出する」ボタンは下部にあるためスクロール
	    scrollBy("400");
	    
	    //「提出する」ボタンをクリックする
	    webDriver.findElement(By.cssSelector("button[type='submit']")).click();
	    
	    //タイトルエビデンス取得
	    String evidenceTitle = "レポート登録 | LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());

	    //エラー出力エビデンス取得
		WebElement intFieldName = webDriver.findElement(By.id("intFieldName_0"));
	    assertTrue(intFieldName.getAttribute("class").contains("errorInput"));
	    
	    //エビデンス証跡取得
	    getEvidence(new Object(){}, "テスト05");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		// 学習項目にSpringBootテストと入力し理解度には何も記述しない
	    WebElement intFieldName = webDriver.findElement(By.id("intFieldName_0"));
	    intFieldName.clear();
	    intFieldName.sendKeys("SpringBootテスト"); 
	    WebElement beforeIntFieldValue = webDriver.findElement(By.id("intFieldValue_0"));
	    beforeIntFieldValue.sendKeys(Keys.HOME); 
	    
	    //「提出する」ボタンは下部にあるためスクロール
	    scrollBy("400");
	    
	    //「提出する」ボタンをクリックする
	    webDriver.findElement(By.cssSelector("button[type='submit']")).click();
	    
	    //タイトルエビデンス取得
	    String evidenceTitle = "レポート登録 | LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());

	    //エラー出力エビデンス取得
		WebElement afterIntFieldValue = webDriver.findElement(By.id("intFieldValue_0"));
	    assertTrue(afterIntFieldValue.getAttribute("class").contains("errorInput"));
	    
	    //エビデンス証跡取得
	    getEvidence(new Object(){}, "テスト06");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		//目標の達成度に数値以外を記述
		WebElement inputTextGoal = webDriver.findElement(By.id("content_0"));
	    inputTextGoal.clear();
	    inputTextGoal.sendKeys("あいうえお");
	    
	    //「提出する」ボタンは下部にあるためスクロール
	    scrollBy("400");
	    
	    //「提出する」ボタンをクリックする
	    webDriver.findElement(By.cssSelector("button[type='submit']")).click();
	    
	    //タイトルエビデンス取得
	    String evidenceTitle = "レポート登録 | LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());

	    //エラー出力エビデンス取得
	    assertTrue(inputTextGoal.getAttribute("class").contains("errorInput"));
	    
	    //エビデンス証跡取得
	    getEvidence(new Object(){}, "テスト07");
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		//目標の達成度に範囲以外の数値を記述
		WebElement inputTextGoal = webDriver.findElement(By.id("content_0"));
		inputTextGoal.clear();
		inputTextGoal.sendKeys("11");
			    
		//「提出する」ボタンは下部にあるためスクロール
		scrollBy("400");
			    
		//「提出する」ボタンをクリックする
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();
		
		 //タイトルエビデンス取得
	    String evidenceTitle = "レポート登録 | LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());

	    //エラー出力エビデンス取得
	    assertTrue(inputTextGoal.getAttribute("class").contains("errorInput"));
	    
	    //エビデンス証跡取得
	    getEvidence(new Object(){}, "テスト08");
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		//目標の達成度を未入力
		WebElement inputTextGoal = webDriver.findElement(By.id("content_0"));
		inputTextGoal.clear();
		
		//所感を未入力
		WebElement inputTextImpression = webDriver.findElement(By.id("content_1"));
	    inputTextImpression.clear();
	    
		//「提出する」ボタンは下部にあるためスクロール
		scrollBy("400");
					    
		//「提出する」ボタンをクリックする
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();
		
		//タイトルエビデンス取得
	    String evidenceTitle = "レポート登録 | LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());

	    //エラー出力エビデンス取得
	    assertTrue(inputTextGoal.getAttribute("class").contains("errorInput"));
	    assertTrue(inputTextImpression.getAttribute("class").contains("errorInput"));
	    
	    //エビデンス証跡取得
	    getEvidence(new Object(){}, "テスト09");
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		//所感に「あ」文字を2001字入力
		String textGoalOver2000 = "あ".repeat(2001);
		WebElement inputTextGoal = webDriver.findElement(By.id("content_0"));
		inputTextGoal.clear();
		inputTextGoal.sendKeys(textGoalOver2000);
				
		//一週間の振り返りに「あ」文字を2001字入力
		String textReviewOver2000 = "あ".repeat(2001);
		WebElement inputTextReview = webDriver.findElement(By.id("content_1"));
		inputTextReview.clear();
		inputTextReview.sendKeys(textReviewOver2000);
			    
		//「提出する」ボタンは下部にあるためスクロール
		scrollBy("400");
							    
		//「提出する」ボタンをクリックする
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();
				
		//タイトルエビデンス取得
		String evidenceTitle = "レポート登録 | LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());

		//エラー出力エビデンス取得
		assertTrue(inputTextGoal.getAttribute("class").contains("errorInput"));
		assertTrue(inputTextReview.getAttribute("class").contains("errorInput"));
		
		//エラー部分を取得したいため下部へスクロール
		scrollBy("200");
		
		//エビデンス証跡取得
		getEvidence(new Object(){}, "テスト10");
	}

}
