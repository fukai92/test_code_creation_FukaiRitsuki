package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;


/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		//ヘッダーにある「機能」をクリック
		webDriver.findElement(By.linkText("機能")).click();
				
		//「機能」にある「ヘルプ」が表示させるまでの時間確保
		visibilityTimeout(By.linkText("ヘルプ"), 5);
				
		//「機能」にある「ヘルプ」をクリック
		webDriver.findElement(By.linkText("ヘルプ")).click();
				
		//タイトルエビデンス取得
		String evidenceTitle = "ヘルプ | LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());
				
		//見出しエビデンス取得
		String evidenceHeading = "ヘルプ";
		String actualHeading = webDriver.findElement(By.tagName("h2")).getText();
		assertEquals(evidenceHeading, actualHeading);
				
		//エビデンス証跡取得
		getEvidence(new Object(){}, "テスト03");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//「よくある質問」リンクをクリック
		webDriver.findElement(By.linkText("よくある質問")).click();
				
		//クリック前のタブIDを取得
		String originalWindow = webDriver.getWindowHandle();
	    // ブラウザで開いているすべてのタブID一覧を取得し、元のタブを削除
		Set<String> handles = webDriver.getWindowHandles();
		handles.remove(originalWindow);
		//新しいタブへ操作を切り替える
	    webDriver.switchTo().window(handles.iterator().next());
			    
		//よくある質問にある「検索」ボタン表示させるまでの時間確保
		visibilityTimeout(By.cssSelector("input[value='検索']"), 5);
				
		//タイトルエビデンス取得
		String evidenceTitle = "よくある質問 | LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());
				
		//検索ボタン表示エビデンス取得
		boolean evidenceButton = webDriver.findElement(By.cssSelector("input[value='検索']")).isDisplayed();
		assertTrue(evidenceButton);
		      	
		//エビデンス証跡取得
		getEvidence(new Object(){}, "テスト04");
	}
	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// TODO ここに追加
	}
	
	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// TODO ここに追加
	}

}
