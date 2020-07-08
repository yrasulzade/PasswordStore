package com.example.passwordstore;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordstore.activities.CreatePasswordActivity;
import com.example.passwordstore.activities.MainActivity;
import com.example.passwordstore.model.Password;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.ViewHolder> {
    private Context context;
    private List<Password> passwordList;
    String TAG = "PasswordAdapter";

    public PasswordAdapter(Context context, List<Password> passwordList) {
        this.context = context;
        this.passwordList = passwordList;
    }

    @NonNull
    @Override
    public PasswordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.password_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordAdapter.ViewHolder holder, int position) {
        Password password = passwordList.get(position);
        holder.title.setText(password.getTitle());
        holder.subtitle.setText(password.getUsername());
    }

    @Override
    public int getItemCount() {
        return passwordList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, subtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleText);
            subtitle = itemView.findViewById(R.id.subtitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("password", passwordList.get(getAdapterPosition()).getPassword());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(context, passwordList.get(getAdapterPosition()).getTitle() +" "+ context.getString(R.string.copy), Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(context, CreatePasswordActivity.class);
                    intent.putExtra("fromWhere", MainActivity.UPDATE_PASSWORD);
                    intent.putExtra("id", passwordList.get(getAdapterPosition()).getId());
                    intent.putExtra("title", passwordList.get(getAdapterPosition()).getTitle());
                    intent.putExtra("subtitle", passwordList.get(getAdapterPosition()).getUsername());
                    intent.putExtra("password", passwordList.get(getAdapterPosition()).getPassword());
                    context.startActivity(intent);

                    return true;
                }
            });
        }
    }
}
