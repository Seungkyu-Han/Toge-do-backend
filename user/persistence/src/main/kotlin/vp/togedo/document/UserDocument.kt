package vp.togedo.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import reactor.core.publisher.Mono
import vp.togedo.util.exception.*

@Document(collection = "user")
data class UserDocument(
    @Id
    var id: ObjectId?,

    @Indexed(unique = true)
    val oauth: Oauth,

    var name: String,

    @Indexed(unique = true)
    var email: String? = null,

    var profileImageUrl: String? = null,

    var friends: MutableSet<ObjectId> = mutableSetOf(),

    var friendRequests: MutableSet<ObjectId> = mutableSetOf(),

    var deviceToken: String? = null,
){
    fun requestFriend(userId: ObjectId): UserDocument{
        if(userId == this.id)
            throw CantRequestToMeException("나에게는 요청할 수 없습니다.")
        if(friends.contains(userId))
            throw AlreadyFriendException("이미 친구인 사용자입니다.")
        if(friendRequests.contains(userId))
            throw AlreadyFriendRequestException("이미 친구 요청이 전송된 상태입니다.")
        this.friendRequests.add(userId)
        return this
    }

    fun approveFriend(userId: ObjectId): Mono<UserDocument> {
        return Mono.fromCallable {
            if(userId == this.id)
                throw CantRequestToMeException("나에게는 요청할 수 없습니다.")
            if(friends.contains(userId))
                throw AlreadyFriendException("이미 친구인 사용자입니다.")
            if(!this.friendRequests.contains(userId)){
                throw NoFriendRequestException("친구 요청이 없었습니다.")
            }
            this.friendRequests.remove(userId)
            this.friends.add(userId)
            this
        }
    }

    fun addFriend(userId: ObjectId): Mono<UserDocument> {
        return Mono.fromCallable {
            if(userId == this.id)
                throw CantRequestToMeException("나에게는 요청할 수 없습니다.")
            if(friends.contains(userId))
                throw AlreadyFriendException("이미 친구인 사용자입니다.")
            this.friends.add(userId)
            this
        }
    }

    fun removeFriend(userId: ObjectId): Mono<UserDocument> {
        return Mono.fromCallable {
            if(userId == this.id)
                throw CantRequestToMeException("나에게는 요청할 수 없습니다.")
            if (this.friends.contains(userId)){
                this.friends.remove(userId)
                this
            }
            else{
                throw NotFriendException("친구가 아닌 사용자입니다.")
            }
        }
    }

    fun removeRequest(userId: ObjectId): Mono<UserDocument> {
        return Mono.fromCallable{
            if(userId == this.id)
                throw CantRequestToMeException("나에게는 요청할 수 없습니다.")
            if(this.friendRequests.contains(userId)){
                this.friendRequests.remove(userId)
                this
            }
            else{
                throw NoFriendRequestException("친구 요청이 없었습니다.")
            }
        }
    }
}

data class Oauth(
    var kakaoId: Long? = null,
    var googleId: String? = null
)