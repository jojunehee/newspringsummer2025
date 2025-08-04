package com.thc.sprbasic2025.util;

import com.thc.sprbasic2025.domain.RefreshToken;
import com.thc.sprbasic2025.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TokenFactory {

    int refreshTokenDuedateTerm = 60;
    int accessTokenDuedateTerm = 10;

    final RefreshTokenRepository refreshTokenRepository;

    public String generateAccessKey(String refreshKey){
        Long userId = validateRefreshTokenKey(refreshKey);
        if(userId != null){
            return generateKey(userId, accessTokenDuedateTerm);
        }
        return null;
    }
    public Long validateRefreshTokenKey(String refreshKey){
        Long userId = null;
        RefreshToken refreshToken = refreshTokenRepository.findByContent(refreshKey);
        if(refreshToken != null){
            userId = validateKey(refreshKey);
        }
        return userId;
    }
    public String generateRefreshKey(Long userId){
        //return generateKey(userId, refreshTokenDuedateTerm);
        revokeRefreshKey(userId);
        String refreshKey = generateKey(userId, refreshTokenDuedateTerm);
        refreshTokenRepository.save(RefreshToken.of(userId, refreshKey));
        return refreshKey;
    }
    public void revokeRefreshKey(Long userId){
        List<RefreshToken> list = refreshTokenRepository.findByUserId(userId);
        refreshTokenRepository.deleteAll(list);
    }
    public String generateKey(Long userId, int dueTerm){
        //무언가 데이터를 받아서, 암호화 해서 돌려줄꺼에요!
        //우리는 유저아이디값, 만료시점을 담아보겠습니다!
        String due = new NowDate().due(dueTerm);
        String key = userId + "_" + due;
        String returnValue = null;
        try{
            returnValue = AES256Cipher.AES_Encode(null, key);
        } catch (Exception e){
        }
        return returnValue;
    }
    public Long validateKey(String key){
        String decodedKey = null;
        try{
            decodedKey = AES256Cipher.AES_Decode(null, key);
            String[] arrayKey = decodedKey.split("_");
            if(arrayKey.length == 2){
                Long userId = Long.parseLong(arrayKey[0]);
                String due = arrayKey[1];
                String now = new NowDate().getNow();

                // 혹시 만료 시간 지났는지 확인!
                String[] tempArray = {due, now};
                Arrays.sort(tempArray);
                System.out.println(Arrays.toString(tempArray));

                if(now.equals(tempArray[0])){
                    //만료가 되지 않았음!
                    return userId;
                }
            }

        } catch (Exception e){
        }
        return null;
    }

}
