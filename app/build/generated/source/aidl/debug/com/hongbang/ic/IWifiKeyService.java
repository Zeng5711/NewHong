/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\win7\\Downloads\\HongbangIC\\HongbangIC\\app\\src\\main\\aidl\\com\\hongbang\\ic\\IWifiKeyService.aidl
 */
package com.hongbang.ic;
public interface IWifiKeyService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.hongbang.ic.IWifiKeyService
{
private static final java.lang.String DESCRIPTOR = "com.hongbang.ic.IWifiKeyService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.hongbang.ic.IWifiKeyService interface,
 * generating a proxy if needed.
 */
public static com.hongbang.ic.IWifiKeyService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.hongbang.ic.IWifiKeyService))) {
return ((com.hongbang.ic.IWifiKeyService)iin);
}
return new com.hongbang.ic.IWifiKeyService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_reloadKeyData:
{
data.enforceInterface(DESCRIPTOR);
this.reloadKeyData();
reply.writeNoException();
return true;
}
case TRANSACTION_setShakeEnabled:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setShakeEnabled(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setEnabled:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setEnabled(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onCommunityChanged:
{
data.enforceInterface(DESCRIPTOR);
this.onCommunityChanged();
reply.writeNoException();
return true;
}
case TRANSACTION_setScreenOnEnabled:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setScreenOnEnabled(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setAutoConn:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setAutoConn(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.hongbang.ic.IWifiKeyService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void reloadKeyData() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_reloadKeyData, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setShakeEnabled(boolean enabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((enabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setShakeEnabled, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setEnabled(boolean enabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((enabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setEnabled, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onCommunityChanged() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onCommunityChanged, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setScreenOnEnabled(boolean enabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((enabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setScreenOnEnabled, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setAutoConn(boolean enabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((enabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setAutoConn, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_reloadKeyData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_setShakeEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_setEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_onCommunityChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_setScreenOnEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_setAutoConn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
}
public void reloadKeyData() throws android.os.RemoteException;
public void setShakeEnabled(boolean enabled) throws android.os.RemoteException;
public void setEnabled(boolean enabled) throws android.os.RemoteException;
public void onCommunityChanged() throws android.os.RemoteException;
public void setScreenOnEnabled(boolean enabled) throws android.os.RemoteException;
public void setAutoConn(boolean enabled) throws android.os.RemoteException;
}
