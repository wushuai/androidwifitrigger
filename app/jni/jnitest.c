#include <stdio.h>
#include <jni.h>
#include "com_test_macowu_jni_NdkJniUtils.h"

jstring JNICALL Java_com_test_macowu_jni_NdkJniUtils_helloFromJNI(JNIEnv *env, jobject object)
{
    return (*env)->NewStringUTF(env, "Hello, JNI");
}

char*   Jstring2CStr(JNIEnv*   env,   jstring   jstr)
{
     char*   rtn   =   NULL;
     jclass   clsstring   =   (*env)->FindClass(env,"java/lang/String");
     jstring   strencode   =   (*env)->NewStringUTF(env,"UTF8");
     jmethodID   mid   =   (*env)->GetMethodID(env,clsstring,   "getBytes",   "(Ljava/lang/String;)[B");
     jbyteArray   barr=   (jbyteArray)(*env)->CallObjectMethod(env,jstr,mid,strencode); // String .getByte("utf8");
     jsize   alen   =   (*env)->GetArrayLength(env,barr);
     jbyte*   ba   =   (*env)->GetByteArrayElements(env,barr,JNI_FALSE);
     if(alen   >   0)
     {
      rtn   =   (char*)malloc(alen+1);         //"\0"
      memcpy(rtn,ba,alen);
      rtn[alen]=0;
     }
     (*env)->ReleaseByteArrayElements(env,barr,ba,0);  //
     return rtn;
}

jstring JNICALL Java_com_test_macowu_jni_NdkJniUtils_getDeviceInfo(JNIEnv *env, jobject object)
{
    jclass build_class = (*env)->FindClass(env, "android/os/Build");
    jfieldID modelId = (*env)->GetStaticFieldID(env, build_class, "MODEL", "Ljava/lang/String;");
    jstring model = (*env)->GetStaticObjectField(env, build_class, modelId);
    char* mp = Jstring2CStr(env, model);

    modelId = (*env)->GetStaticFieldID(env, build_class, "BRAND", "Ljava/lang/String;");
    jstring brand = (*env)->GetStaticObjectField(env, build_class, modelId);
    char* mb = Jstring2CStr(env, brand);

    char* mc = "-";

    return (*env)->NewStringUTF(env, strcat(strcat(mp, mc), mb));
}