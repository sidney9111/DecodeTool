##3种渠道打包模式
####基于XML
在./AndroidManifest.xml文件的<application>段内容添加meta,不同渠道略有不同，例如：
1.万普
'''xml
<meta-data android:name="APP_PID" android:value="gfan" />
...
'''
2.有米
'''xml
<meta-data android:name="YOUMI_CHANNEL" android:value="10020"></meta-data>
...
'''
3.大头鸟
'''xml
<meta-data android:name="DTN_PLACE_ID" android:value="gfan" />
...
'''
####基于Assets文件
如果程序猿不希望key和channelid明文显示在xml中，可能通过代码获取一个外部标识，标识文件名称为
>[channel].dt
'''java
File file = new File("[channel].dt");
String channel = file.getName();
'''
###自定义文件
(暂缺)

##一般渠道配置规则

##渠道分类
| key       | description   | value validate   |
| ---       | ---        | ---  |
| channel      | 渠道名称   | 唯一   |
| channel_code   | 渠道编号   |     |
| meta_data       | ---  |xml模式     |
|meta_on|所在内容段|xml模式|


