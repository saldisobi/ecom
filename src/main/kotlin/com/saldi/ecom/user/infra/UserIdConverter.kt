package com.saldi.ecom.user.infra

import com.saldi.ecom.user.domain.UserId
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class UserIdConverter : AttributeConverter<UserId, String> {
    override fun convertToDatabaseColumn(attribute: UserId) = attribute.value
    override fun convertToEntityAttribute(dbData: String) = UserId(dbData)
}