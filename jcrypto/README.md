# Run code

```bash
# view all services
./gradlew app:run -Dorg.bouncycastle.fips.approved_only=true --args="inspect BCFIPS"
# view algorithms for a specific service
./gradlew app:run -Dorg.bouncycastle.fips.approved_only=true --args="inspect BCFIPS -service SecretKeyFactory" 
```
