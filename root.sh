#!/system/bin/sh
rm /data/local/atvc/blop.asec
rm /data/local/atvc/blop
mkdir /data/local/atvc/dalvik-cache
ANDROID_DATA=/data/local/atvc dalvikvm -cp /data/local/atvc/pie.jar com.a.b.Exploit
