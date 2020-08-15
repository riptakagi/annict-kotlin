package jp.annict.rest.v1.models

import jp.annict.rest.v1.enums.Action
import jp.annict.rest.v1.enums.RatingState
import java.util.*

/**
 * 画像関係
 */
data class Facebook(
    val og_image_url: String?
)

data class Twitter(
    val mini_avatar_url     : String?,
    val normal_avatar_url   : String?,
    val bigger_avatar_url   : String?,
    val original_avatar_url : String?,
    val image_url           : String?
)

data class Images(
    val recommended_url : String?,
    val facebook        : Facebook?,
    val twitter         : Twitter?
)

/**
 * ユーザ情報
 */
data class User(
    val id                   : Long?,
    val username             : String?,
    val name                 : String?,
    val description          : String?,
    val url                  : String?,
    val avatar_url           : String?,
    val background_image_url : String?,
    val records_count        : Long?,
    val followings_count     : Long?,
    val followers_count      : Long?,
    val wanna_watch_count    : Long?,
    val watching_count       : Long?,
    val watched_count        : Long?,
    val on_hold_count        : Long?,
    val stop_watching_count  : Long?,
    val created_at           : Date?
)

data class Status(val kind: Status?)

/**
 * 作品情報
 */
data class Work (
    val id                : Long?,
    val title             : String?,
    val title_kana        : String?,
    val media             : String?,
    val media_text        : String?,
    val season_name       : String?,
    val season_name_text  : String?,
    val released_on       : Date?,
    val released_on_about : Date?,
    val official_site_url : String?,
    val wikipedia_url     : String?,
    val twitter_username  : String?,
    val twitter_hashtag   : String?,
    val episodes_count    : Long?,
    val watchers_count    : Long?,
    val reviews_count     : Long?,
    val syobocal_tid      : Long?,
    val mal_anime_id      : Long?,
    val images            : Images?,
    val no_episodes       : Boolean?
)

/**
 * エピソード情報
 */
data class Episode (
    val id                    : Long?,
    val number                : Long?,
    val number_text           : String?,
    val sort_number           : Long?,
    val title                 : String?,
    val records_count         : Long?,
    val record_comments_count : Long?,
    val work                  : Work?,
    val prev_episode          : Episode?,
    val next_episode          : Episode?
)

/**
 * エピソードへの記録
 */
data class Record(
    val id             : Long?,
    val comment        : String?,
    val rating         : Long?,
    val is_modified    : Boolean?,
    val rating_state   : RatingState?,
    val likes_count    : Long?,
    val comments_count : Long?,
    val created_at     : Date?,
    val user           : User?,
    val work           : Work?,
    val episode        : Episode?
)

/**
 * アクティビティ情報
 */
data class Activity (
    val id         : Long?,
    val user       : User?,
    val action     : Action,
    val created_at : Date?,
    val episode    : Episode?,
    val record     : Record?
)

/**
 * シリーズ情報
 */
data class Series (
    val id      : Long?,
    val name    : String?,
    val name_ro : String?,
    val name_en : String?
)

/**
 * キャラクター情報
 */
data class Character (
    val id                        : Long?,
    val name                      : String?,
    val name_kana                 : String?,
    val name_en                   : String?,
    val nickname                  : String?,
    val birthday                  : String?,
    val birthday_en               : String?,
    val age                       : String?,
    val age_en                    : String?,
    val blood_type                : String?,
    val blood_type_en             : String?,
    val height                    : String?,
    val height_en                 : String?,
    val weight                    : String?,
    val weight_en                 : String?,
    val nationality               : String?,
    val nationality_en            : String?,
    val occupation                : String?,
    val occupation_en             : String?,
    val description_source        : String?,
    val description_source_en     : String?,
    val favorite_characters_count : Long?,
    val series                    : Series?
)

/**
 * 都道府県情報
 */
data class Prefecture (
    val id   : Long?,
    val name : String?
)

/**
 * 人物(声優)情報
 */
data class Person (
    val id                    : Long?,
    val name                  : String?,
    val name_kana             : String?,
    val name_en               : String?,
    val nickname              : String?,
    val nickname_en           : String?,
    val gender_text           : String?,
    val url                   : String?,
    val url_en                : String?,
    val wikipedia_url         : String?,
    val wikipedia_url_en      : String?,
    val twitter_username      : String?,
    val twitter_username_en   : String?,
    val birthday              : String?,
    val blood_type            : String?,
    val height                : Long?,
    val favorite_people_count : Long?,
    val casts_count           : Long?,
    val staffs_count          : Long?,
    val prefecture            : Prefecture?
)

/**
 * キャスト情報
 */
data class Cast (
    val id          : Long?,
    val name        : String?,
    val name_en     : String?,
    val sort_number : Long?,
    val work        : Work?,
    val character   : Character?,
    val person      : Person?
)

/**
 * プロフィール情報
 */
data class Me (
    val id                   : Long?,
    val username             : String?,
    val name                 : String?,
    val description          : String?,
    val url                  : String?,
    val avatar_url           : String?,
    val background_image_url : String?,
    val records_count        : Long?,
    val followings_count     : Long?,
    val followers_count      : Long?,
    val wanna_watch_count    : Long?,
    val watching_count       : Long?,
    val watched_count        : Long?,
    val on_hold_count        : Long?,
    val stop_watching_count  : Long?,
    val created_at           : Date?,
    val email                : String?,
    val notifications_count  : Long?
)

/**
 *　チャンネル情報
 */
data class Channel (
    val id   : Long?,
    val name : String?
)

/**
 * 放送予定情報
 */
data class Program (
    val id             : Long?,
    val started_at     : Date?,
    val is_rebroadcast : Boolean?,
    val channel        : Channel?,
    val work           : Work?,
    val episode        : Episode?
)

/**
 * 団体(会社)情報
 */
data class Organization (
    val id                           : Long?,
    val name                         : String?,
    val name_kana                    : String?,
    val name_en                      : String?,
    val url                          : String?,
    val url_en                       : String?,
    val wikipedia_url                : String?,
    val wikipedia_url_en             : String?,
    val twitter_username             : String?,
    val twitter_username_en          : String?,
    val favorite_organizations_count : Long?,
    val staffs_count                 : Long?
)

/**
 * スタッフ情報
 */
data class Staff (
    val id            : Long?,
    val name          : String?,
    val name_en       : String?,
    val role_text     : String?,
    val role_other    : String?,
    val role_other_en : String?,
    val sort_number   : String?,
    val work          : String?,
    val organization  : Organization?
)

/**
 * 作品へのレビュー情報
 */
data class Review (
    val id                     : Long?,
    val title                  : String?,
    val body                   : String?,
    val rating_animation_state : RatingState?,
    val rating_music_state     : RatingState?,
    val rating_story_state     : RatingState?,
    val rating_character_state : RatingState?,
    val rating_overall_state   : RatingState?,
    val likes_count            : Long?,
    val impressions_count      : Long?,
    val modified_at            : Date?,
    val created_at             : Date?,
    val user                   : User?,
    val work                   : Work?
)

data class Application(val uid: String?)