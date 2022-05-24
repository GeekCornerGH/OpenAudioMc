package gg.hangouts.deputy.shared.redis.packets.openaudiomc;

import com.craftmend.openaudiomc.generic.authentication.objects.ServerKeySet;
import com.craftmend.openaudiomc.generic.networking.abstracts.AbstractPacketPayload;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinPacket extends AbstractPacketPayload {

    private String playerName;
    private UUID playerUuid;
    private UUID serverId;

}
