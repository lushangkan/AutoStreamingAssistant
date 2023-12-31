# NetworkPacket
Packets for server and client communication.

### ClientBoundPacket

- RequestStatus

Request client status.

```json5
{
  "version": "{{ version }}"
}
```

- RequestBindStatus

Request bind status.

    No body.

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

- ClientStatus

Client status.

```json5
{
  "status": "ready", // Enum: ClientStatus
  "version": "{{ version }}"
}
```

- BindStatus

Bind status.

```json5
{
  "playerUuid": "{{ player's uuid }}" // Null if unbind
}
```

- ManualBindCamera

This packet is sent when the user binds manually using a command.

```json5
{
  "playerUuid": "{{ player's uuid }}"
}
```

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
