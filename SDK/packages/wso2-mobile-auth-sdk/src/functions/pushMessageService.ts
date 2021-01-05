import AsyncStorage from "@react-native-community/async-storage";

/**
 * Class for handling push message device tokens
 */
export class PushMessageService {
  /**
   * Save the push message device token in React Native Aysnc Storage
   *
   * @param token Push messege device token
   */
  public static async savePushToken(token: string) {
    if (token) {
      // user has a device token
      await AsyncStorage.setItem("fcmToken", token);
      console.log("Push Token" + token);

      // TODO: change the name of the async storage item name to be more generic
    } else {
      throw new Error("Push token not available");
    }
  }

  /**
   * Update push message device token if a change is identified
   *
   * @param token New push message device token
   * @param saveToken Boolean true if saving token internally
   */
  public static async updatePushToken(token: string, saveToken?: boolean) {
    if (token) {
      //TODO: Add functionality to send request to update Push token in server

      // Update token in async storage
      if (saveToken) {
        this.savePushToken(token);
        console.log("Push token updated");
      }
    } else {
      throw new Error("Push token not available");
    }
  }
}
