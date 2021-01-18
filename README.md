# CustomKeyboard
自定义键盘


<div align="center">
    <img src="https://github.com/FPhoenixCorneaE/CustomKeyboard/blob/master/images/alipay-dialog.png" width="200" align="top"/>
	<img src="https://github.com/FPhoenixCorneaE/CustomKeyboard/blob/master/images/idcard-keyboard.png" width="200" align="top" style="margin-left:15px"/>
</div>


-----------------------------------------


How to include it in your project:
--------------
**Step 1.** Add the JitPack repository to your build file
```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

**Step 2.** Add the dependency
```groovy
dependencies {
	implementation 'com.github.FPhoenixCorneaE:CustomKeyboard:1.0.4'
}
```

密码框
-----------------------------

```xml
<com.fphoenixcorneae.keyboard.PasswordLayout
        android:id="@+id/passwordLayout"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:layout_marginStart="20dp"
        android:layout_marginEnd="20dp"
        android:layout_marginBottom="30dp"
        app:layout_constraintBottom_toTopOf="@+id/digitKeyboard" />
```

##### 设置密码
```kotlin
passwordLayout.setPassword(digitKeyboard)
```

数字键盘
--------------------------
```xml
<com.fphoenixcorneae.keyboard.DigitKeyboardView
        android:id="@+id/digitKeyboard"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent" />
```

##### 随机数字
```kotlin
digitKeyboard.randomDigit = true
```

##### 密码变化监听
```kotlin
digitKeyboard.onPasswordChangedListener = { digitKeyboard, isCompleted ->
            passwordLayout.setPassword(digitKeyboard)
        }
```


支付宝输入支付密码弹窗
----------------------------------
```kotlin
AlipayDialog(this)
                .apply {
                    // 是否随机数字
                    randomDigit = true
                    // 支付取消监听器
                    onPayCancelListener = {

                    }
                    // 忘记密码监听器
                    onPasswordForgetListener = {

                    }
                    // 密码变化监听器
                    onPasswordChangedListener = { digitKeyboard, isCompleted ->

                    }
                }
                .show()
```

身份证号码键盘
----------------------------------
```kotlin
// 隐藏系统软键盘
        etIdCardNo.hideSystemSoftKeyboard()
        idCardKeyboard.apply {
            // 身份证号码变化监听
            onIdCardNoChangedListener = {
                etIdCardNo.text = Editable.Factory.getInstance().newEditable(it)
                etIdCardNo.setSelection(it.length)
            }
        }
```