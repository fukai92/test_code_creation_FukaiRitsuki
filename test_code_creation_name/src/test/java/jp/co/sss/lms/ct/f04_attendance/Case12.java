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
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

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
						
		//確認ダイアログで「OK」ボタンをクリック
		webDriver.switchTo().alert().accept();
								
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
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {
		//「勤怠情報を直接編集する」をクリック
		webDriver.findElement(By.linkText("勤怠情報を直接編集する")).click();
		
		//勤怠情報変更画面にある出勤ボタン表示までの時間確保
		visibilityTimeout(By.cssSelector("button.default-button"), 5);
		
		//タイトルエビデンス取得
		String evidenceTitle = "勤怠情報変更｜LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());
								
		//「定時」ボタン表示エビデンス取得
		boolean evidenceButton = webDriver.findElement(By.cssSelector("button.default-button")).isDisplayed();
		assertTrue(evidenceButton);
								
		//エビデンス証跡取得
		getEvidence(new Object(){}, "テスト04");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() {
		// 1行目の出勤「時」だけ選択し、「分」を未選択（空文字）にする
		webDriver.findElement(By.cssSelector("#startHour0 option[value='9']")).click();
		webDriver.findElement(By.cssSelector("#startMinute0 option[value='']")).click();
		
		//「更新」ボタンが下部にあるためスクロール
		scrollBy("400");

		//「更新」ボタンをクリック
		webDriver.findElement(By.cssSelector("input.update-button")).click();
		
		//確認ダイアログで「OK」ボタンをクリック
		webDriver.switchTo().alert().accept();
		
		//勤怠情報変更画面にある出勤ボタン表示までの時間確保
		visibilityTimeout(By.cssSelector("button.default-button"), 5);
				
		//タイトルエビデンス取得
		String evidenceTitle = "勤怠情報変更｜LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());
										
		//「定時」ボタン表示エビデンス取得
		boolean evidenceButton = webDriver.findElement(By.cssSelector("button.default-button")).isDisplayed();
		assertTrue(evidenceButton);

		//エラーメッセージが表示されることを確認
		WebElement errorMessage = webDriver.findElement(By.cssSelector("ul .error"));
		assertTrue(errorMessage.isDisplayed());

		//エビデンス取得
		getEvidence(new Object(){}, "テスト05");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() {
		// 1行目の出勤を空白にして退勤に入力を行う
		webDriver.findElement(By.cssSelector("#startHour0 option[value='']")).click();
		webDriver.findElement(By.cssSelector("#startMinute0 option[value='']")).click();
		webDriver.findElement(By.cssSelector("#endHour0 option[value='9']")).click();
		webDriver.findElement(By.cssSelector("#endMinute0 option[value='0']")).click();
				
		//「更新」ボタンが下部にあるためスクロール
		scrollBy("400");

		//「更新」ボタンをクリック
		webDriver.findElement(By.cssSelector("input.update-button")).click();
				
		//確認ダイアログで「OK」ボタンをクリック
		webDriver.switchTo().alert().accept();
				
		//勤怠情報変更画面にある出勤ボタン表示までの時間確保
		visibilityTimeout(By.cssSelector("button.default-button"), 5);
						
		//タイトルエビデンス取得
		String evidenceTitle = "勤怠情報変更｜LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());
												
		//「定時」ボタン表示エビデンス取得
		boolean evidenceButton = webDriver.findElement(By.cssSelector("button.default-button")).isDisplayed();
		assertTrue(evidenceButton);

		//エラーメッセージが表示されることを確認
		WebElement errorMessage = webDriver.findElement(By.cssSelector("ul .error"));
		assertTrue(errorMessage.isDisplayed());

		//エビデンス取得
		getEvidence(new Object(){}, "テスト06");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() {
		// 1行目の出勤時間を「12：00」、退勤時間を「9：00」に指定
		webDriver.findElement(By.cssSelector("#startHour0 option[value='12']")).click();
		webDriver.findElement(By.cssSelector("#startMinute0 option[value='0']")).click();
		webDriver.findElement(By.cssSelector("#endHour0 option[value='9']")).click();
		webDriver.findElement(By.cssSelector("#endMinute0 option[value='0']")).click();
						
		//「更新」ボタンが下部にあるためスクロール
		scrollBy("400");

		//「更新」ボタンをクリック
		webDriver.findElement(By.cssSelector("input.update-button")).click();
						
		//確認ダイアログで「OK」ボタンをクリック
		webDriver.switchTo().alert().accept();
		
		//勤怠情報変更画面にある出勤ボタン表示までの時間確保
		visibilityTimeout(By.cssSelector("button.default-button"), 5);
								
		//タイトルエビデンス取得
		String evidenceTitle = "勤怠情報変更｜LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());
														
		//「定時」ボタン表示エビデンス取得
		boolean evidenceButton = webDriver.findElement(By.cssSelector("button.default-button")).isDisplayed();
		assertTrue(evidenceButton);

		//エラーメッセージが表示されることを確認
		WebElement errorMessage = webDriver.findElement(By.cssSelector("ul .error"));
		assertTrue(errorMessage.isDisplayed());

		//エビデンス取得
		getEvidence(new Object(){}, "テスト07");
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() {
		//1行目の出勤時間を「9：00」、退勤時間を「10：00」に指定
		webDriver.findElement(By.cssSelector("#startHour0 option[value='9']")).click();
		webDriver.findElement(By.cssSelector("#startMinute0 option[value='0']")).click();
		webDriver.findElement(By.cssSelector("#endHour0 option[value='10']")).click();
		webDriver.findElement(By.cssSelector("#endMinute0 option[value='0']")).click();

		//1行目の中抜け時間を2時間に設定
		webDriver.findElement(By.cssSelector("select[name='attendanceList[0].blankTime'] option[value='120']")).click();
		
		//「更新」ボタンが下部にあるためスクロール
		scrollBy("400");

		//「更新」ボタンをクリック
		webDriver.findElement(By.cssSelector("input.update-button")).click();
						
		//確認ダイアログで「OK」ボタンをクリック
		webDriver.switchTo().alert().accept();
		
		//勤怠情報変更画面にある出勤ボタン表示までの時間確保
		visibilityTimeout(By.cssSelector("button.default-button"), 5);
										
		//タイトルエビデンス取得
		String evidenceTitle = "勤怠情報変更｜LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());
																
		//「定時」ボタン表示エビデンス取得
		boolean evidenceButton = webDriver.findElement(By.cssSelector("button.default-button")).isDisplayed();
		assertTrue(evidenceButton);

		//エラーメッセージが表示されることを確認
		WebElement errorMessage = webDriver.findElement(By.cssSelector("ul .error"));
		assertTrue(errorMessage.isDisplayed());

		//エビデンス取得
		getEvidence(new Object(){}, "テスト08");
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {
		//1行目の出勤時間を「9：00」、退勤時間を「18：00」に指定
		webDriver.findElement(By.cssSelector("#startHour0 option[value='9']")).click();
		webDriver.findElement(By.cssSelector("#startMinute0 option[value='0']")).click();
		webDriver.findElement(By.cssSelector("#endHour0 option[value='18']")).click();
		webDriver.findElement(By.cssSelector("#endMinute0 option[value='0']")).click();
				
		//1行目の備考欄に101文字入力
		String text101 = "あ".repeat(101);
		WebElement noteInput = webDriver.findElement(By.name("attendanceList[0].note"));
		noteInput.clear();
		noteInput.sendKeys(text101);
		
		//「更新」ボタンが下部にあるためスクロール
		scrollBy("400");

		//「更新」ボタンをクリック
		webDriver.findElement(By.cssSelector("input.update-button")).click();
								
	    //確認ダイアログで「OK」ボタンをクリック
		webDriver.switchTo().alert().accept();
		
		//勤怠情報変更画面にある出勤ボタン表示までの時間確保
		visibilityTimeout(By.cssSelector("button.default-button"), 5);
						
		//タイトルエビデンス取得
		String evidenceTitle = "勤怠情報変更｜LMS";
		assertEquals(evidenceTitle, webDriver.getTitle());
												
		//「定時」ボタン表示エビデンス取得
		boolean evidenceButton = webDriver.findElement(By.cssSelector("button.default-button")).isDisplayed();
		assertTrue(evidenceButton);

		//エラーメッセージが表示されることを確認
		WebElement errorMessage = webDriver.findElement(By.cssSelector("ul .error"));
		assertTrue(errorMessage.isDisplayed());

		//エビデンス取得
		getEvidence(new Object(){}, "テスト09");
	}

}
