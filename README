# Twitter ユーザ発言Feed化アプリケーション

## 必要な物
* Google appengine デプロイ環境
* Twitter アプリケーション登録

## 使用方法
1. appengine-web.xmlにデプロイ先を指定
2. twitter4j.propertiesにTwitterのコンシューマキー・シークレットキーを設定
3. デプロイし、/auth/にアクセスしTwitterの認証を実施
4. /feed/name/[スクリーン名]にアクセス

## PubSubHubbub対応
フィードにアクセス時、更新があればpubsubhubbub.appengine.comに更新通知を実施する。
/round/にアクセスすると、一度アクセスした事のあるTwitterユーザのフィードにアクセスし、更新確認を実施する。
cron.xmlで8分ごとに/round/にアクセスする。TwitterのAPI制限の範囲内で頻度を上げることができる。
