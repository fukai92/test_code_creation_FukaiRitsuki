package jp.co.sss.lms.ct.f04_attendance;

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
import org.openqa.selenium.WebElement;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		//ヘッダーにある「勤怠」をクリック
		webDriver.findElement(By.linkText("勤怠")).click();
				
		//勤怠情報変更画面にある出勤ボタン表示までの時間確保
		visibilityTimeout(By.cssSelector("input[name='punchIn']"), 5);
				
		//タイトルエビデンス取得
		String evidenceTitle = "勤怠情報変更｜LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());
				
		//「出勤」ボタン表示エビデンス取得
      	boolean evidenceButton = webDriver.findElement(By.cssSelector("input[name='punchIn']")).isDisplayed();
      	assertTrue(evidenceButton);
				
		//エビデンス証跡取得
		getEvidence(new Object(){}, "テスト03");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {
		//出勤ボタンをクリック
		webDriver.findElement(By.cssSelector("input[name='punchIn']")).click();
		
		//確認ダイアログで「OK」ボタンをクリック
		webDriver.switchTo().alert().accept();
		
		//勤怠情報変更画面にある退勤ボタン表示までの時間確保
		visibilityTimeout(By.cssSelector("input[name='punchOut']"), 5);
		
		//タイトルエビデンス取得
		String evidenceTitle = "勤怠情報変更｜LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());
		
		//テスト当日の日程が下部にあるためスクロール
		scrollBy("200");
		
		//出勤表示エビデンス取得
		WebElement startTime = webDriver.findElement(By.cssSelector("tr.info td:nth-child(3)"));
	    String startTimeEvidence = startTime.getText();
	    assertFalse(startTimeEvidence.isEmpty());
						
	    //エビデンス証跡取得
		getEvidence(new Object(){}, "テスト04");
		
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {
		//退勤ボタンをクリック
		webDriver.findElement(By.cssSelector("input[name='punchOut']")).click();
				
		//確認ダイアログで「OK」ボタンをクリック
	    webDriver.switchTo().alert().accept();
				
		//勤怠情報変更画面にある出勤ボタン表示までの時間確保
		visibilityTimeout(By.cssSelector("input[name='punchIn']"), 5);
				
		//タイトルエビデンス取得
		String evidenceTitle = "勤怠情報変更｜LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());
		
		//テスト当日の日程が下部にあるためスクロール
		scrollBy("200");
								
		//退勤表示エビデンス取得
		WebElement endTime = webDriver.findElement(By.cssSelector("tr.info td:nth-child(4)"));
		String endTimeEvidence = endTime.getText();
		assertFalse(endTimeEvidence.isEmpty());
								
		//エビデンス証跡取得
		getEvidence(new Object(){}, "テスト05");
	}

}
