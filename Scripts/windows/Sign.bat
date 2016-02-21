
@ECHO OFF
Echo Auto-sign Created By Dave Da illest 1 
Echo Update.zip is now being signed and will be renamed to update_signed.zip

%java -jar signapk.jar testkey.x509.pem testkey.pk8 update.zip update_signed.zip%
java -jar Auto-sign/signapk.jar Auto-sign/testkey.x509.pem Auto-sign/testkey.pk8 Auto-sign/s.apk s_signed.apk

Echo Signing Complete 
 
Pause