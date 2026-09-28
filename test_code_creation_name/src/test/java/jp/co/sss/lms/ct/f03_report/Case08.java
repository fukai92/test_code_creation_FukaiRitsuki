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

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//「提出済」の文字列を含む行の中にある「詳細」ボタンを取得してクリック
	    By unsubmittedDetailBtn = By.xpath("//tr[td/span[text()='提出済']]//input[@value='詳細']");
	    webDriver.findElement(unsubmittedDetailBtn).click();
		
		//セクション詳細画面にある「戻る」ボタン表示までの時間確保
		visibilityTimeout(By.cssSelector("input[value='戻る']"), 5);
		
		//タイトルエビデンス取得
		String evidenceTitle = "セクション詳細 | LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());
		
		//「戻る」ボタン表示エビデンス取得
		boolean evidenceButton = webDriver.findElement(By.cssSelector("input[value='戻る']")).isDisplayed();
		assertTrue(evidenceButton);
		
		//「戻る」ボタン証跡を取るため下へスクロール
		scrollBy("200");
				
		//エビデンス証跡取得
		 getEvidence(new Object(){}, "テスト03");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//「提出済み日報【デモ】を確認する」ボタンをクリックする
		webDriver.findElement(By.cssSelector("input[value*='を確認する']")).click();
				
		//レポート登録画面にある「提出する」ボタン表示までの時間確保
		visibilityTimeout(By.cssSelector("button[type='submit']"), 5);
				
		//タイトルエビデンス取得
		String evidenceTitle = "レポート登録 | LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());
				
		//「提出する」ボタン表示エビデンス取得
		boolean evidenceButton = webDriver.findElement(By.cssSelector("button[type='submit']")).isDisplayed();
		assertTrue(evidenceButton);
				
		//エビデンス証跡取得
		getEvidence(new Object(){}, "テスト04");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		// TODO ここに追加
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		// TODO ここに追加
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// TODO ここに追加
	}

}
