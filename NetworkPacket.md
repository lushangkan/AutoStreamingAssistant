# NetworkPacket
Packets for server and client communication.

### ClientBoundPacket

- BindCamera

Bind the camera to a player.

```json
{
  "playerUuid": "{{ player's uuid }}"
}
```

- UnbindCamera

Unbind the camera from player.

    No body.

---

### ServerBoundPacket

- BindCameraResponse

Response to BindCamera.

```json5
{
  "success": true,
  "result": "success" // Enum: BindResult
}
```

- UnbindCameraResponse

Response to UnbindCamera.

```json5
{
  "success": true,
  "result": "success" // Enum: UnbindResult
}
```