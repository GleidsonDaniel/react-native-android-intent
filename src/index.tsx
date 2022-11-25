import { NativeModules } from 'react-native';

export function openLink(url: string, packageName: string): Promise<void> {
  return NativeModules.AndroidIntent.openLink(url, packageName);
}

export function isPackageInstalled( packageName: string): Promise<boolean> {
  return NativeModules.AndroidIntent.isPackageInstalled(packageName);
}