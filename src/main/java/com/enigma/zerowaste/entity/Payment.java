package com.enigma.zerowaste.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mst_payment")
public class Payment {
    @Id
    @Column(name = "id_payment")
    private String id;
    @SerializedName("external_id")
    private String externalId;
    @SerializedName("user_id")
    private String userId;
    private String status;
    @SerializedName("merchant_name")
    private String merchantName;
    @SerializedName("merchant_profile_picture_url")
    private String merchantProfilePictureUrl;
    private Double amount;
    @SerializedName("paid_amount")
    private Double paidAmount;
    @SerializedName("bank_code")
    private String bankCode;
    @SerializedName("paid_at")
    private String paidAt;
    @SerializedName("payer_email")
    private String payerEmail;
    private String description;
    @SerializedName("expiry_date")
    private String expiryDate;
    @SerializedName("invoice_url")
    private String invoiceUrl;
    @SerializedName("should_exclude_credit_card")
    private Boolean shouldExcludeCreditCard;
    @SerializedName("adjusted_received_amount")
    private Double adjustedReceivedAmount;
    @SerializedName("should_send_email")
    private Boolean shouldSendEmail;
    @SerializedName("fees_paid_amount")
    private Double feesPaidAmount;
    private String created;
    private String updated;
    private String currency;
    @SerializedName("payment_channel")
    private String paymentChannel;
    @SerializedName("payment_method")
    private String paymentMethod;
    @SerializedName("payment_destination")
    private String paymentDestination;

    @OneToOne(mappedBy = "payment")
    private Order order;
}
